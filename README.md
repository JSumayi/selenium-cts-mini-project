# Google Cognizant Search Automation – Selenium Mini Project

## 📌 Project Overview
This project is a **Selenium WebDriver automation mini project** developed using **Java, TestNG, and Maven**.  
The goal is to automate Google search functionality for the keyword **“Cognizant”**, extract links, validate search suggestions, navigate across different Google tabs, and capture screenshots.

This project demonstrates **real‑time automation concepts** such as synchronization, reusable methods, assertions, and cross‑browser testing.

---

## 🎯 Problem Statement
- Find and print all links displayed on Google homepage  
- Search for **Cognizant**  
- Capture auto‑suggestions and print them  
- Navigate across Google tabs (All, News, Images, Videos)  
- Capture full‑page screenshots  
- Extract and print result counts  
- Support cross‑browser execution  

---

## 🛠️ Tech Stack
- **Programming Language:** Java (JDK 21)  
- **Automation Tool:** Selenium WebDriver 4.36.0  
- **Testing Framework:** TestNG 7.11.0  
- **Build Tool:** Maven  
- **Browsers Supported:** Chrome, Edge, Firefox  
- **IDE:** Eclipse / IntelliJ IDEA  

---

## 📂 Project Structure
```text
selenium-cts-mini-project
│
├── src
│   └── test
│       └── java
│           └── com.selenium.miniProject
│               └── GoogleCognizantTest.java
│
├── screenshots
│   └── (Captured screenshots with timestamp)
│
├── pom.xml
└── testng.xml

Test Scenarios Automated

Open Google homepage
Count and print all links on Google
Enter “Cognizant” in search box
Capture and print search suggestions
Click Google Search and validate results count
Navigate to All tab and capture screenshot
Navigate to News tab and capture screenshot
Navigate to Images tab and capture screenshot
Navigate to Videos tab and capture screenshot
Close browser


🧪 Test Execution Flow
Execution order is controlled using TestNG priority:

openGoogleAndPrintLinks
typeCognizantAndCaptureSuggestions
findSearchResultsCount
clickAllTab
clickNewsTab
clickImagesTab
clickVideosTab


🖥️ Cross‑Browser Testing
Cross‑browser execution is handled using TestNG parameters.

📸 Screenshot Handling

Screenshots captured using TakesScreenshot
Stored inside screenshots folder
Timestamp added to avoid overwriting


⏳ Synchronization Strategy

Explicit waits using WebDriverWait
Custom wait logic to handle:

Dynamic Google elements
Slow loading pages
CAPTCHA scenarios




✔️ Assertions

SoftAssert used to allow execution of all validations
assertAll() ensures proper failure reporting at the end of each test


📊 Test Reports

TestNG generates HTML reports automatically
Location:

Plain Texttest-output/CrossBrowserSuite/ChromeTest.htmlShow more lines

🚧 Challenges Faced

Handling dynamic Google search suggestions
CAPTCHA interruptions during execution
Result count not always displayed


🔧 Future Enhancements

Implement Page Object Model (POM)
Integrate Extent / Allure Reports
Parallel execution using Selenium Grid
CI integration using Jenkins
Logging using Log4j


✅ Conclusion
This project helped in gaining hands‑on experience with:

Real‑time Selenium automation
Dynamic web element handling
Cross‑browser testing using TestNG
Writing reusable and maintainable automation code
