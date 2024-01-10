FROM  jre:17-22.04_edge

COPY target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

