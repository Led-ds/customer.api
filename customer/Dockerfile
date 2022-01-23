FROM openjdk:8

WORKDIR /app

COPY target/customer-0.0.1-SNAPSHOT.jar /app/customer-api-app.jar

#ENTRYPOINT ["java", "-jar","customer-api-app.jar"]

CMD java -jar customer-api-app.jar $APP_OPTIONS

EXPOSE 8080