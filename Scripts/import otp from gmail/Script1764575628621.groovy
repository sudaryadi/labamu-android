import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable as GlobalVariable
import gmail.GmailUtils
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

GmailUtils gmail = new GmailUtils()

String otp = gmail.getLatestOTP("Labamu OTP", 6)

println("OTP received: " + otp)

// Use OTP in your app
Mobile.waitForElementPresent(findTestObject('Object Repository/OTP Page/input_otp_locator'), 30)
Mobile.setText(findTestObject("Object Repository/OTP Page/input_otp_locator"), otp, 30)