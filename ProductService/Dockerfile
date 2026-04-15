FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="Ord00"

WORKDIR /app

COPY pom.xml .
COPY ./Model/pom.xml ./Model/
COPY ./ProductService/pom.xml ./ProductService/

RUN mvn dependency:go-offline -B

COPY ./Model/src ./Model/src
COPY ./ProductService/src ./ProductService/src

RUN mvn clean package -pl ProductService -am

FROM eclipse-temurin:17-jre-jammy AS final

VOLUME /tmp

RUN groupadd -r products && useradd -r -g products products
USER products

WORKDIR /app

COPY --from=build /app/ProductService/target/ProductService-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]