# FakeRESTApi

## Description

This project is designed to test RESTful APIs using TestNG and Rest Assured with parallel execution and dynamic
configuration via parameters. Reports are generated using Allure.

## Main Technologies

- **Java 24**
- **Maven**
- **TestNG**
- **Rest Assured**
- **Allure Reports**

## Configuration Files

All environment-specific test settings are stored in the `configs/` directory.

The test framework dynamically loads a .properties file based on the -Denv parameter.
For example, -Denv=dev will load configs/dev.properties.

## Running Tests

You can run the tests using Maven with configurable parameters for environment, API version, and parallel thread count.

### Default Maven Parameters

By default, the following parameters are used when running tests:

```bash
-Denv=dev -DapiVersion=1 -DthreadCount=3
```

## Manual Execution via GitHub Actions

Tests can also be executed manually through the GitHub Actions UI:

Go to the Actions tab.

Select the Regression run workflow.

Click Run workflow and choose the environment (e.g., dev).

## Allure Report
After test execution, an Allure HTML report is automatically generated and published via GitHub Pages.

You can view the latest report here:
https://valerarv4.github.io/FakeRESTApi/
