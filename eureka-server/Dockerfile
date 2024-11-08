FROM openjdk:21
EXPOSE 8081
WORKDIR /stock-notification-app
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","eureka-server-0.0.1-SNAPSHOT.jar"]