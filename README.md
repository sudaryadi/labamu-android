
# Katalon Studio Android Automation for Labaku app

============================================================

📁 Project Structure
Profiles/
└── default

Test Cases/
└── tc_1 - login email

Object Repository/
└── OTP Page/
    └── input_otp_locator

Keywords/
└── gmail/
    └── GmailUtils.groovy

============================================================

🔧 Prerequisites
Required:

- Katalon Studio 8+ (Mobile/Web compatible)
- JavaMail (included in Katalon)
- Jsoup (bundled with Katalon)
- A Gmail account with:
	- IMAP enabled
	- App Password (required for automation)

============================================================

🔐 Environment Variables (GlobalVariable)
Set these inside Profiles → default:
| Variable             | Description                   |
| -------------------- | ----------------------------- |
| `GMAIL_USERNAME`     | Gmail email address           |
| `GMAIL_APP_PASSWORD` | Gmail app password (16-digit) |

============================================================

## Steps to Run ##
1. Open Katalon Studio
2. Import this project
3. Set Global Variables:
   - GMAIL_USERNAME='email@gmail.com' // your gmail
   - GMAIL_APP_PASSWORD='aaaa bbbb cccc dddd' // your gmail app password // (https://myaccount.google.com/apppasswords)
4. Execute TestCases/tc_1 - login email in Android
5. Check Reports

============================================================

🤖 Run in Real CI (GitHub Actions / Jenkins / GitLab CI)

Environment Variables Required

In the CI secret vault, add:
GMAIL_USERNAME
GMAIL_APP_PASSWORD

## GitHub Actions CI
.github/workflows/katalon.yml

name: Katalon Tests

on:
  push:
  pull_request:

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Download Katalon Runtime Engine
      run: |
        wget https://download.katalon.com/katalon/KRE_LATEST/katalon.zip
        unzip katalon.zip

    - name: Run Test Suite
      env:
        GMAIL_USERNAME: ${{ secrets.GMAIL_USERNAME }}
        GMAIL_APP_PASSWORD: ${{ secrets.GMAIL_APP_PASSWORD }}
      run: |
        ./katalonc \
          -noSplash -runMode=console \
          -projectPath="MyProject.prj" \
          -testSuitePath="Test Suites/CI Suite" \
          -browserType="Chrome" \
          -executionProfile="default"
