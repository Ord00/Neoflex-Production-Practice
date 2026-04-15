FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="Ord00"

WORKDIR /app

COPY pom.xml .
COPY ./Model/pom.xml ./Model/
COPY ./OrdersService/pom.xml ./OrdersService/

RUN mvn dependency:go-offline -B

COPY ./Model/src ./Model/src
COPY ./OrdersService/src ./OrdersService/src

RUN mvn clean package -pl OrdersService -am

FROM eclipse-temurin:17-jre-jammy AS final

VOLUME /tmp

RUN groupadd -r orders && useradd -r -g orders orders
USER orders

WORKDIR /app

COPY --from=build /app/OrdersService/target/OrdersService-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]