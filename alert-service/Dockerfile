FROM openjdk:21
EXPOSE 8081
WORKDIR /stock-notification-app
COPY target/alert-service-0.0.1-SNAPSHOT.jar alert-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","alert-service-0.0.1-SNAPSHOT.jar"]