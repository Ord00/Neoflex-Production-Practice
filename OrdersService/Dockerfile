FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="Ord00"

WORKDIR /app

COPY ./Model ./Model/
COPY ./OrdersService ./OrdersService/

RUN cd Model && mvn clean install

RUN cd OrdersService && mvn clean package

FROM eclipse-temurin:17-jre-jammy AS final

RUN groupadd -r orders && useradd -r -g orders orders
USER orders

WORKDIR /app

COPY --from=build /app/OrdersService/target/OrdersService-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]