[![Build Status](https://travis-ci.org/shankar-ray/mload.svg?branch=master)](https://travis-ci.org/shankar-ray/mload)

# mLoad

#### A Java project to upload custom metadata types to Salesforce 

* 1/10/2017 - custom metadata single record upsert operation successful
* 1/11/2017 - process csv to perform bulk upsert
* 1/12/2017 - remove hardcoding of credentials and allow user to login with username-password
* 1/12/2017 - improve csv processing to provide API names from csv headers
* 1/12/2017 - created credentialsManger bean for session management
* 1/13/2017 - project supports gradle build
* 1/14/2017 - updated project to JavaFX UI
* 1/17/2017 - custom metadata name selection to be a drop-down
* 1/21/2017 - allow user to login via server url
* 1/21/2017 - allow user to have columns in any order in the csv file
* 1/22/2017 - supported single Jar/cross-platform executable build using fatJar
* toto - NSIS support for windows
* todo - remove linting errors from LandingPageUIController
* todo - validate csv headers using metadata describe
* todo - support delete feature
* any suggestions ?

# Steps to build and run the app
#### STEP 1 -> Install JDK // I used 32 bit JDK, even when on on 64bit Win 10 machine
#### STEP 2 -> Download Gradle.zip and extract // I used 32 bit, even when on on 64bit Win 10 machine
#### STEP 3 -> Add JAVA_HOME environment variable to point to JDK directory and GRADLE_HOME to point to gradle directory and JDK/bin, Gradle/bin directory to Path varialbe
#### STEP 4 -> clone/download this reporsitory
#### STEP 5 -> switch to the downloaded directory
#### STEP 6 -> open command prompt and type gradle fatJar
#### STEP 7 -> the executable Jar will be generated inside the $project/build/libs/ folder

## Sample CSV structure. Column order can be anything.

| ApiKey__c | Label | Email__c           | Username__c | DeveloperName |
|-----------|-------|--------------------|-------------|---------------|
| A1001     | 1     | max@developer1.com | max1        | X1            |
| A1002     | 2     | max@developer2.com | max2        | X2            |

* Note - DeveloperName and Label are mandatory fields in the CSV file. Other fields also can be mandatory if they are required fields in the Salesforce instance/org.
