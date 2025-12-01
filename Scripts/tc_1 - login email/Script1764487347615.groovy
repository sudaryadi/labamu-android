import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory


Mobile.startExistingApplication('id.co.labamu.app', FailureHandling.STOP_ON_FAILURE)

// Tunggu sampai tombol "Masuk Dengan Email" terlihat
Mobile.waitForElementPresent(findTestObject('Object Repository/Login Page/button_Masuk dengan email_locator'), 30)

// Klik tombol "Masuk Dengan Email"
Mobile.tap(findTestObject('Object Repository/Login Page/button_Masuk dengan email_locator'), 10)

Mobile.waitForElementPresent(findTestObject('Object Repository/Login Page/input_email_locator'), 40)

// Input valid email
Mobile.setText(findTestObject('Object Repository/Login Page/input_email_locator'), GlobalVariable.GMAIL_USERNAME, 10)

// Centang checkbox "Syarat & Ketentuan dan Kebijakan Privasi"
Mobile.tap(findTestObject('Object Repository/Login Page/checkbox_syarat dan ketentuan_locator'), 10)

// Assert tombol "Masuk" sudah aktif (enabled)
boolean isMasukButtonEnabled = Mobile.verifyElementAttributeValue(findTestObject('Object Repository/Login Page/button_masuk is enabled_locator'), 
    'enabled', 'true', 10, FailureHandling.CONTINUE_ON_FAILURE)

if (isMasukButtonEnabled) {
    Mobile.comment('SUCCESS: Tombol Masuk sudah aktif dan bisa diklik')
} else {
    Mobile.fail('ERROR: Tombol Masuk masih disabled')
}

// Klik tombol "Masuk" yang sudah enable
Mobile.tap(findTestObject('Object Repository/Login Page/button_masuk is enabled_locator'), 10)

// Tunggu halaman OTP muncul
Mobile.waitForElementPresent(findTestObject('Object Repository/OTP Page/input_otp_locator'), 30)

// Ambil otp dari gmail
WebUI.callTestCase(findTestCase('Test Cases/import otp from gmail'), [:], FailureHandling.STOP_ON_FAILURE)

// Tunggu sampai masuk dan logo profil terlihat
Mobile.waitForElementPresent(findTestObject('Object Repository/Profile Page/logo_profile_locator'), 30)

// Ambil screenshot tampilan page terakhir
Mobile.takeScreenshot('Screenshot/result.PNG', FailureHandling.STOP_ON_FAILURE)

// Menutup aplikasi
Mobile.closeApplication()