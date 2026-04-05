FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /src

COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /src/target/orders-service-java-0.1.0.jar /app/orders-service-java.jar

ENV PORT=8080

EXPOSE 8080

CMD ["java", "-jar", "/app/orders-service-java.jar"]
