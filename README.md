# API Test Automation Framework

This project is an **API Test Automation Framework** built using **Java**, **RestAssured**, **Cucumber**, and **Maven**. It is designed for testing REST APIs with dynamic request payloads, response validation, and detailed logging. The framework is structured for easy maintainability and scalability as more API tests are added.

## Table of Contents
1. [Technologies](#technologies)
2. [Project Structure](#project-structure)
3. [Setup and Installation](#setup-and-installation)
4. [How to Use](#how-to-use)
   - [Global Configuration](#global-configuration)
   - [Step Definitions](#step-definitions)
   - [Feature Files](#feature-files)
   - [Hooks](#hooks)
   - [Logging](#logging)
5. [Running Tests](#running-tests)
6. [Test Data](#test-data)
7. [Future Enhancements](#future-enhancements)

---

## Technologies
- **Java** (JDK 11+)
- **RestAssured** (API Testing Library)
- **Cucumber** (Behavior-Driven Development Framework)
- **JUnit** (Test runner for Cucumber)
- **Maven** (Dependency and Build Management)

## Project Structure

```
├── src
│   ├── main
│   │   └── java
│   │       └── pojo                            # POJO classes (currently unused but kept for future use)
│   ├── test
│   │   ├── java
│   │   │   └── cucumber.Options                # Test runner configuration
│   │   │   └── features                        # Cucumber feature files
│   │   │   │   └── siteEndpointsValidation.feature
│   │   │   └── resources                       # Helper and utility classes
│   │   │   │   └── APIResources.java           # Enum for API resources (endpoints)
│   │   │   │   └── TestDataBuild.java          # Methods for dynamic JSON payload generation
│   │   │   │   └── Utils.java                  # Utility class for configuration and logging
│   │   │   └── stepDefinations                 # Step definitions mapped to feature file steps
│   │   │       └── StepDefinition.java         # Core step definitions
│   │   │       └── Hooks.java                  # Hooks for setting up pre-conditions
├── pom.xml                                      # Maven dependencies and plugins
├── logging.txt                                  # Log file for API requests and responses
├── README.md                                    # Documentation (this file)
└── .classpath, .project                         # IDE-related files (if applicable)
```

## Setup and Installation

### Prerequisites:
- **Java 11+** installed.
- **Maven** installed and configured.
- An IDE like **IntelliJ IDEA** or **Eclipse** is recommended for working with the project.

### Steps:
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo-url.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd APIFramework
   ```

3. **Install dependencies**:
   Run Maven to install all required dependencies:
   ```bash
   mvn clean install
   ```

4. **Configure global properties**:
   Update the `global.properties` file located in `src/test/java/resources`:
   ```properties
   baseUrl=https://promiseq-staging-api.promiseq.com
   
   #Headers For Site EndPoint
   promiseq-subscription-key=hIprIAtfykOknaSUG3tZ
   site-id=Site 5000
   site-name=Test Site test
   is-hardware-device=false
   ```

## How to Use

### Global Configuration
The **`global.properties`** file contains environment-specific configurations such as the base URL and default headers. These values are fetched dynamically within the `Utils.java` class, which helps manage configuration and logging.

### Step Definitions
The **step definitions** in the `stepDefinations` folder map the Gherkin steps in the feature files to Java code. The key class here is `StepDefinition.java`, which contains methods to call API resources and validate responses.

- **Example**:
  ```java
  @When("user calls {string} with {string} http request for site")
  public void user_calls_with_http_request_for_site(String resource, String method) {
      APIResources resourceAPI = APIResources.valueOf(resource);
      if (method.equalsIgnoreCase("PUT")) {
          response = res.when().put(resourceAPI.getResource());
      } else if (method.equalsIgnoreCase("GET")) {
          response = res.when().get(resourceAPI.getResource());
      }
  }
  ```

### Feature Files
Feature files, written in **Gherkin syntax**, define the test scenarios in a readable format. The `features` folder contains test scenarios for site creation, fetching, and deletion.

- **Example Feature File** (`siteEndpointsValidation.feature`):
  ```gherkin
  Feature: Site API Automation
  
    @CreateSite
    Scenario: Verify if Site is created successfully using CreateSiteAPI
      Given default headers for site are provided
      When user calls "createSiteAPI" with "PUT" http request for site
      Then the API call got success with status code 200
      And store the site_id from response

    @FetchSite
    Scenario: Verify if Site is fetched successfully using FetchSiteAPI
      Given Fetch Site Payload with stored site_id
      When user calls "fetchSiteAPI" with "GET" http request for site
      Then the API call got success with status code 200
      And "name" in response body is "Test Site test"
  ```

### Hooks
Hooks are used to ensure certain conditions are met before or after a test scenario runs. In this project, **`Hooks.java`** ensures that a site is created if a deletion is required and the `siteId` is not available.

- **Example Hook**:
  ```java
  @Before("@DeleteSite")
  public void beforeDeleteSiteScenario() throws IOException {
      StepDefinition stepDef = new StepDefinition();
      if (StepDefinition.siteId == null) {
          // Create a site if siteId is null
          stepDef.custom_headers_for_site_are_provided(new Utils().getDefaultHeaders());
          stepDef.user_calls_with_http_request_for_site("createSiteAPI", "PUT");
          StepDefinition.siteId = stepDef.response.jsonPath().getString("site_id");
      }
  }
  ```

### Logging
All requests and responses are logged to `logging.txt`. The log file is cleared at the start of each run to ensure fresh logs for every test execution.

- **Log Example**:
  ```
  ===================== API Request =====================
  Timestamp: 2024-09-25 14:30:52.123
  ======================================================
  Headers:
    promiseq-subscription-key: hIprIAtfykOknaSUG3tZ
    site-id: Site 5000
    site-name: Test Site test
    is-hardware-device: false
  Request URI: https://promiseq-staging-api.promiseq.com/site
  Request method: PUT
  Status Code: 200
  Response Body:
  {
    "site_id": "Site 5000",
    "client_id": "client-c7a1-e15a-a901",
    "name": "Test Site test",
    "createdAt": "2024-09-25T14:30:51.630Z"
  }
  ```

## Running Tests
You can run the tests via **Maven**:
```bash
mvn test
```

This will execute all the scenarios defined in your feature files and log the request and response details.

## Test Data
- **`TestDataBuild.java`** is designed to generate dynamic JSON payloads for site creation and deletion. Example methods include:
   - `createSitePayload(String siteId, String siteName, boolean isHardwareDevice)`
   - `deleteSitePayload(String siteId)`

These methods allow the flexibility to modify payloads dynamically.

## Future Enhancements
- **Expand POJO Usage**: Implement serialization/deserialization using POJOs for better request/response handling.
- **Enhanced Logging**: Add more detailed logs, such as response times and more granular failure logs.
- **Additional Test Cases**: Extend test coverage to include error codes, edge cases, and different API methods.

---
