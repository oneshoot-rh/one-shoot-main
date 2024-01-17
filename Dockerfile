FROM  jre:17-22.04_edge

COPY target/*.jar /app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]

