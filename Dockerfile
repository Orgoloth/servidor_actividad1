FROM openjdk:11

WORKDIR /app

COPY . .

RUN ./mvnw clean package

RUN cp ./target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]