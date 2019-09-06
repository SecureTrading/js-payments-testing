ARG CODE_VERSION=develop
ARG CODE_REPO=js-payments
FROM securetrading1/${CODE_REPO}:${CODE_VERSION}
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/js-payments/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
