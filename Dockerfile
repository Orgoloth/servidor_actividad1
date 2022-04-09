FROM maven:3.8.4 AS build
WORKDIR /usr/src/app
COPY src .
COPY pom.xml .
RUN mvn -f ./pom.xml clean package

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/usr/src/app/target/*.jar
COPY --from=build ${JAR_FILE} /usr/app/app.jar
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]