FROM eclipse-temurin:24-jdk-alpine
MAINTAINER otee.dev
COPY target/swipe-0.0.1-SNAPSHOT.jar swipe.jar
EXPOSE 3001
ENTRYPOINT ["java","-Xmx128m", "-jar","swipe.jar","--server.port=3001"]
