FROM  jre:17-22.04_edge

COPY target/*.jar /tenantService.jar

EXPOSE 1111:1111

ENTRYPOINT ["java","-jar","/tenantService.jar"]

