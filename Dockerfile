ARG  CODE_VERSION=bugfix-ST-201-mocks-issues
FROM securetrading1/js-payments:${CODE_VERSION}
RUN apk update && apk add openjdk8 make nss
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/js-payments/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
