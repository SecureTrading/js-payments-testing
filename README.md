# js-payments-testing
The integration testing framework used in order to test the js-payments interface

- Running tests on travis does not require any action or changes within framework,
- Tests can be run on any machine in two different ways:

1) Using chromedriver.exe binary(should be used mainly for tests execution during development)
    - change property "TARGET" to "localChrome" in //resources/application.properties
    - run tests using command "mvn install" in console
2)  - change property "TARGET" to "remote" in //resources/application.properties
    - change property "BROWSERSTACK_USERNAME" to real browserstack username in //resources/application.properties
    - change property "BROWSERSTACK_ACCESS_KEY" to real browserstack user_key in //resources/application.properties
    - run wiremock server with command "make run_wiremock" (wiremock standalone .jar is included within framework)
    - run browserstack local connection tunnel with command "BrowserStackLocal.exe --key <real browserstack user_key> --local-identifier local_id" (BrowserStackLocal.exe binary is included within framework)
    - run tests using one of options available in Makefile, for example: "make test_chrome_68_w10", or "make run_all_in_parallel".