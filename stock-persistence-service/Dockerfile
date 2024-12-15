FROM openjdk:21
EXPOSE 8083
WORKDIR /stock-notification-app
COPY target/stock-persistence-service-0.0.1-SNAPSHOT.jar stock-persistence-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","stock-persistence-service-0.0.1-SNAPSHOT.jar"]