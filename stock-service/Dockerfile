FROM openjdk:21
EXPOSE 8081
WORKDIR /stock-notification-app
COPY target/stock-service-0.0.1-SNAPSHOT.jar stock-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","stock-service-0.0.1-SNAPSHOT.jar"]