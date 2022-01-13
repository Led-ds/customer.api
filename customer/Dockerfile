FROM openjdk:8

WORKDIR /app

COPY target/customer-0.0.1-SNAPSHOT.jar /app/customer-api-app.jar

ENTRYPOINT ["java", "-jar","customer-api-app.jar"]

EXPOSE 8080