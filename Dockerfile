ARG CODE_VERSION=feature-STJS-100-cc-automated-tests
FROM securetrading1/js-payments:${CODE_VERSION}
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/js-payments/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
