FROM openjdk:21-bookworm
EXPOSE 9090:8787
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]