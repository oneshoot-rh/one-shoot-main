FROM  jre:17-22.04_edge

COPY target/*.jar /one-shoot-main.jar

EXPOSE 1111:1111

ENTRYPOINT ["java","-jar","/one-shoot-main.jar"]

