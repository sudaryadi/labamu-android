package gmail

import javax.mail.*
import javax.mail.internet.MimeMultipart
import javax.mail.internet.MimeUtility
import org.jsoup.Jsoup
import com.kms.katalon.core.annotation.Keyword
import internal.GlobalVariable

class GmailUtils {

	@Keyword
	String getLatestOTP(String subjectKeyword, int otpLength = 6) {

		String host = "imap.gmail.com"
		String user = GlobalVariable.GMAIL_USERNAME
		String pass = GlobalVariable.GMAIL_APP_PASSWORD
		String folderName = "INBOX"

		Properties props = new Properties()
		props.put("mail.store.protocol", "imaps")

		Session session = Session.getInstance(props, null)
		Store store = session.getStore("imaps")
		store.connect(host, user, pass)

		Folder folder = store.getFolder(folderName)
		folder.open(Folder.READ_ONLY)

		Message[] messages = folder.getMessages()

		// newest → oldest
		for (int i = messages.length - 1; i >= 0; i--) {
			Message msg = messages[i]

			if (!msg.subject.toLowerCase().contains(subjectKeyword.toLowerCase())) {
				continue
			}

			String body = extractText(msg)

			// 1️⃣ HTML-specific OTP extraction: <div class="otp-code">159251</div>
			String htmlOTP = extractHtmlOtp(body)
			if (htmlOTP != null) {
				println("📩 HTML OTP found: $htmlOTP")
				folder.close(false)
				store.close()
				return htmlOTP
			}

			// 2️⃣ Fallback to generic 6-digit regex
			String otp = extractOTP(body, otpLength)
			if (otp != null) {
				println("📨 Regex OTP found: $otp")
				folder.close(false)
				store.close()
				return otp
			}
		}

		folder.close(false)
		store.close()
		return null
	}


	/** Extract raw text from email */
	private String extractText(Message message) {

		Object content = message.getContent()

		if (content instanceof String) {
			return decodeQuotedPrintable(content)
		}

		if (content instanceof MimeMultipart) {
			return getMultipartText(content)
		}

		return ""
	}


	/** Convert quoted-printable to plain text */
	private String decodeQuotedPrintable(String text) {
		try {
			InputStream is = MimeUtility.decode(
					new ByteArrayInputStream(text.getBytes("UTF-8")),
					"quoted-printable"
					)
			return new String(is.readAllBytes(), "UTF-8")
		} catch (Exception e) {
			return text
		}
	}


	/** Parse multipart emails (HTML preferred) */
	private String getMultipartText(MimeMultipart multipart) {
		String result = ""

		for (int i = 0; i < multipart.count; i++) {
			BodyPart bp = multipart.getBodyPart(i)

			if (bp.isMimeType("text/plain")) {
				return decodeQuotedPrintable(bp.getContent() as String)
			}

			if (bp.isMimeType("text/html")) {
				String html = decodeQuotedPrintable(bp.getContent() as String)
				result += Jsoup.parse(html).text()
			}
		}

		return result
	}


	/** Extract OTP from specific HTML selector */
	private String extractHtmlOtp(String body) {
		try {
			def doc = Jsoup.parse(body)
			def otp = doc.select("div.otp-code").text()
			if (otp && otp.isNumber()) return otp
		} catch (Exception ignored) {}
		return null
	}


	/** Generic 6-digit regex fallback */
	private String extractOTP(String text, int otpLength) {
		def matcher = text =~ /(\d{$otpLength})/
		return matcher.find() ? matcher.group(1) : null
	}
}
