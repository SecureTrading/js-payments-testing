FROM alpine:latest
RUN apk update && apk add nodejs npm git openjdk8 make
COPY . /app
WORKDIR /app/js-payments
RUN git pull --ff-only origin develop
RUN npm install && npm run prod
RUN cp -r dist/* /app/src/main/resources/__files
WORKDIR /app
EXPOSE 8760
EXPOSE 8443
CMD ["make", "run_wiremock"]
