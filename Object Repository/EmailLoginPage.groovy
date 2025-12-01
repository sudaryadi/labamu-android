
package Pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject

class EmailLoginPage {
    TestObject fieldEmail = new TestObject('emailField').addProperty('xpath', 
        com.kms.katalon.core.testobject.ConditionType.EQUALS, "//android.widget.EditText")
    TestObject checkboxTerms = new TestObject('termsCheckbox').addProperty('xpath',
        com.kms.katalon.core.testobject.ConditionType.EQUALS, "//android.widget.CheckBox")
    TestObject buttonMasuk = new TestObject('masukButton').addProperty('xpath',
        com.kms.katalon.core.testobject.ConditionType.EQUALS, "//android.widget.Button[@text='Masuk']")

    void enterEmail(String email) {
        Mobile.setText(fieldEmail, email, 10)
    }

    void toggleTerms() {
        Mobile.tap(checkboxTerms, 10)
    }

    boolean isMasukEnabled() {
        return Mobile.getAttribute(buttonMasuk, 'enabled', 10) == 'true'
    }

    void tapMasuk() {
        Mobile.tap(buttonMasuk, 10)
    }
}
