FROM alpine:latest
RUN apk update && apk add nodejs npm git openjdk8 make
COPY . /app
WORKDIR /app/js-payments
RUN git fetch -a
RUN git checkout feature/STJS-16-input-components-with-default-style
RUN npm install && npm run prod
RUN cp -r dist/* /app/src/main/resources/__files
# COPY /app/js-payments/dist /app/src/main/resources/__files/dist
WORKDIR /app
EXPOSE 8760
CMD ["make", "run_wiremock"]
