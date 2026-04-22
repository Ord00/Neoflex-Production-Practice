FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="Ord00"

WORKDIR /app

COPY ./Model ./Model/
COPY ./ProductService ./ProductService/

RUN cd Model && mvn clean install

RUN cd ProductService && mvn clean package

FROM eclipse-temurin:17-jre-jammy AS final

RUN groupadd -r products && useradd -r -g products products
USER products

WORKDIR /app

COPY --from=build /app/ProductService/target/ProductService-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]