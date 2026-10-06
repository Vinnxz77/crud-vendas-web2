FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src src
RUN mvn -B clean package
FROM tomcat:11.0.2-jdk17-temurin
COPY --from=build /build/target/vendas.war /usr/local/tomcat/webapps/vendas.war
