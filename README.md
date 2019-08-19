# js-payments-testing
The integration testing framework used in order to test the js-payments interface

- Running tests on travis requires defining two travis variables (directly in travis.yml file or in travis interface):
    - TESTS_TYPE: possible values: fullTest/smokeTest (full or limited number of browsers/devices on which tests will be run)
    - TEST_TAG: here can be defined tests with specific tag will be run, for example:
        '@smokeTest' -> smoke tests only
        '@fullTest' -> full set of tests
        '@fullTest,~@animatedCard' -> full tests but without animated card tests
        '@animatedCard' -> animated card tests only

- Tests can be run on any machine in two different ways:

1) Using chromedriver.exe binary(should be used mainly for tests execution during development)
    - change property "TARGET" to "localChrome" in //resources/application.properties
    - run tests using command "mvn install" in console
2)  - change property "TARGET" to "remote" in //resources/application.properties
    - change property "BROWSERSTACK_USERNAME" to real browserstack username in //resources/application.properties
    - change property "BROWSERSTACK_ACCESS_KEY" to real browserstack user_key in //resources/application.properties
    - build docker containing test page and wiremock server with command "docker build . --tag 'securetrading1/js-payments-testing'"
    - run docker containing test page and wiremock server with command "docker run -d -p 8443:8443 -it 'securetrading1/js-payments-testing'"
    - run browserstack local connection tunnel with command "BrowserStackLocal.exe --key <real browserstack user_key> --local-identifier local_id" (BrowserStackLocal.exe binary is included within framework)
    - run tests using one of options available in Makefile, for example: "test_chrome_76_w10" or use one of maven command specified in travis.yml file:
        ' mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=76.0 -Dresolution=1920x1080 -Dcucumber.options="--tags $TEST_TAG" ' -> as $TEST_TAG set specific tests tag
