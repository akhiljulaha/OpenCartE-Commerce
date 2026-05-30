# OpenCart E-Commerce Web Automation

End-to-end UI test automation framework for the [OpenCart](https://naveenautomationlabs.com/opencart/) demo e-commerce application, built with *Selenium WebDriver + TestNG + Maven* following the Page Object Model (POM) design pattern.

---

## Tech Stack

- Language: Java 1.8+
- Build Tool: Apache Maven 3.9+
- Browser Automation: Selenium WebDriver 4.11.0
- Test Runner: TestNG 7.8.0
- Reporting: ExtentReports 5.1.1
- Reporting: Allure (TestNG) 2.27.0
- Data Driven: Apache POI (Excel) 5.2.5
- AOP / Allure: AspectJ Weaver 1.9.22.1
- CI: Jenkins (Jenkinsfile)

---

## Project Structure

```text
OpenCartE-Commerce-Web/
├── pom.xml                                  # Maven build config & dependencies
├── Jenkinsfile                              # Jenkins CI pipeline
├── src/
│   ├── main/
│   │   ├── java/com/qa/opencart/
│   │   │   ├── base/                        # BaseTest setup/teardown
│   │   │   ├── factory/                     # DriverFactory, OptionsManager
│   │   │   ├── pages/                       # Page Object classes
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── AccountsPage.java
│   │   │   │   ├── RegisterPage.java
│   │   │   │   ├── ResultsPage.java
│   │   │   │   ├── ProductInfoPage.java
│   │   │   │   └── PDP.java
│   │   │   ├── pojo/                        # POJO classes (Product, etc.)
│   │   │   ├── utils/                       # ElementUtil, ExcelUtil, JavaScriptUtil, AppConstants
│   │   │   ├── listeners/                   # ExtentReport, Allure, AnnotationTransformer, Retry
│   │   │   └── frameworkexception/          # Custom FrameworkException
│   │   └── resources/
│   │       ├── config/                      # config.properties (env: qa, stage, dev, uat)
│   │       └── testrunners/                 # testng_regression.xml, testng_chrome.xml
│   └── test/
│       └── java/com/qa/opencart/tests/      # TestNG test classes
└── target/                                  # Build & report output
```

---

## Features

- *Page Object Model (POM)* — clean separation of locators, actions, and tests
- *Cross-Browser Support* — Chrome, Firefox, Edge (configurable via config.properties)
- *Parallel Execution* — TestNG parallel tests via thread-count in suite XML
- *Selenium Grid Ready* — toggle remote=true to run against a Selenium Grid hub
- *Data-Driven Testing* — TestNG @DataProvider and Excel-based data via Apache POI
- *Retry Logic* — automatic retry for flaky tests via Retry listener
- *Dual Reporting* — ExtentReports + Allure with screenshots on failure
- *Multi-Environment Configs* — qa, dev, stage, uat property files
- *Headless / Incognito / Highlight* modes via config flags
- *CI/CD* — Jenkins pipeline defined in Jenkinsfile

---

## Prerequisites

- *JDK 8 or higher* (project compiles to Java 1.8 target; tested with JDK 25)
- *Apache Maven 3.6+*
- *Google Chrome* (or Firefox / Edge) installed locally
  - ChromeDriver is auto-managed by Selenium Manager (bundled in Selenium 4.11+)
- (Optional) *Jenkins* for CI runs
- (Optional) *Allure CLI* to view Allure reports locally

### Verify your environment

```bash
java -version
mvn -version
```

---

## Setup

1. *Clone the repo*

   ```bash
   git clone <repo-url>
   cd OpenCartE-Commerce-Web
   ```

2. *Install dependencies*

   ```bash
   mvn clean install -DskipTests
   ```

3. *Configure the run*

   Edit [src/main/resources/config/config.properties](src/main/resources/config/config.properties):

   ```properties
   browser   = chrome           # chrome | firefox | edge
   incognito = true
   headless  = false
   highlight = true
   url       = https://naveenautomationlabs.com/opencart/index.php?route=account/login
   username  = <your-username>
   password  = <your-password>
   remote    = false            # set true for Selenium Grid
   huburl    = http://localhost:4444/wd/hub
   ```

---

## Running Tests

### Run the default regression suite

```bash
mvn test
```

The suite executed is defined by the suiteXmlFile property in pom.xml
(default: [src/main/resources/testrunners/testng_regression.xml](src/main/resources/testrunners/testng_regression.xml)).

### Run a specific suite

```bash
mvn test -DsuiteXmlFile=src/main/resources/testrunners/testng_chrome.xml
```

### Run a single test class

```bash
mvn test -Dtest=LoginPageTest
```

### Run with a specific browser (override)

```bash
mvn test -Dbrowser=firefox
```

### Clean previous output and re-run

```bash
mvn clean test
```

---

## Reports

### Extent Reports

Generated automatically at the end of the run:

```text
target/test-output/ExtentReports/extent-report.html
```

Open in any browser.

### Allure Reports

Raw results are written to allure-results/. To generate and view:

```bash
allure serve allure-results
```

(install Allure CLI: brew install allure on macOS)

### Surefire Reports

Standard Maven test reports:

```text
target/surefire-reports/
```

---

## Test Suites

| Suite XML | Purpose |
| --- | --- |
| testng_regression.xml | Full regression — Login, Accounts, Search, Product Info |
| testng_chrome.xml     | Chrome-only smoke run |
| testng.master.xml     | Master / driver suite |

---

## CI / Jenkins

A Jenkinsfile is included for Jenkins pipeline execution. A Jenkinsfile_sample is also provided as a reference template. Configure a Jenkins job pointing at this repo's Jenkinsfile to enable automated builds.

---

## Author

*Akhil*

---

## License

This project is for educational and internal automation purposes.