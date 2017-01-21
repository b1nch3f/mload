Copyright 2016 the original author or authors.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

# A Salesforce Custom Metadata Loader
### Gradle build automation using launch4j to perform Jar to win32 executable format
### JARs from Maven Repository -> https://mvnrepository.com/artifact/com.force.api
#### I'm using JDK 32-bit even when i'm on 64-bit win 10. 
#### GUI development is under Netbeans IDE

#### A Java project to perform full-curd operations on custom metadata type

* 1/10/2017 - custom metadata single record upsert operation successful
* 1/11/2017 - process csv to perform bulk upsert
* 1/12/2017 - remove hardcoding of credentials and allow user to login with username-password
* 1/12/2017 - improve csv processing to provide API names from csv headers
* 1/12/2017 - created credentialsManger bean for session management
* 1/13/2017 - project supports gradle build
* 1/14/2017 - updated project to JavaFX UI
* 1/17/2017 - object name selection to be a drop-down
* 1/21/2017 - allow user to login via server url
* 1/21/2017 - allow user to have columns in any order in the csv file
* todo - remove linting errors from LandingPageUIController
* todo - validate csv headers with actual API names using metadata describe
* todo - support delete feature
* any suggestions ?

# Steps to build the app
#### STEP 1 -> Install JDK // I used 32 bit JDK, even when on on 64bit Win 10 machine
#### STEP 2 -> Download Gradle.zip and extract // I used 32 bit, even when on on 64bit Win 10 machine
#### STEP 3 -> Add JAVA_HOME environment variable to point to JDK directory and GRADLE_HOME to point to gradle directory and JDK/bin, Gradle/bin directory to Path varialbe
#### STEP 4 -> clone/download this reporsitory
#### STEP 5 -> switch to the downloaded directory
#### STEP 6 -> open command prompt and type gradle createExe
#### STEP 7 -> the .exe will be generated inside the build/launch4j folder