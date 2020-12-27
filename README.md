#### Project stack :
- Gradle project 
- Rest-assured as API testing library
- JUnit5 as test runner xUnit framework
- Project is building on Travis CI
- Allure as test reporting
- JavaOwner for usefull config

#### Required environment
- Java 8

#### Run tests from cli:
```
 ./gradlew clean test
```

#### Generate Allure report
- generate report after full test run
```
 ./gradlew allureReport
```
- then run Allure web service
```
 ./gradlew allureServe
```
