ARG  CODE_VERSION=latest
FROM securetrading1/js-payments:${CODE_VERSION}
RUN apk update && apk add openjdk8 make
COPY . /app/js-payments-testing
WORKDIR /app/js-payments-testing
RUN cp -r /app/js-payments/dist/* /app/js-payments-testing/src/main/resources/__files
EXPOSE 8760
EXPOSE 8443
ENTRYPOINT ["make", "run_wiremock"]
