FROM  openjdk:17-jdk-slim

COPY target/*.jar /one-shoot-main.jar

EXPOSE 1111:1111

ENTRYPOINT ["java","-jar","/one-shoot-main.jar"]

