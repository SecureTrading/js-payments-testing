ARG CODE_VERSION=feature-ST-311-travis-configuration
FROM securetrading1/js-payments-card:${CODE_VERSION}
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/js-payments-card/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
