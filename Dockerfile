ARG CODE_VERSION=bugfix-ST-344-failing-pipeline
ARG CODE_REPO=js-payments-card
FROM securetrading1/${CODE_REPO}:${CODE_VERSION}
ARG CODE_REPO
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/${CODE_REPO}/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
