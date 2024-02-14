FROM  openjdk:17-jdk-slim

COPY target/*.jar /one-shoot-main.jar

ENTRYPOINT ["java","-jar","/one-shoot-main.jar"]

