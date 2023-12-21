FROM eclipse-temurin:17
ARG JAR_FILE=*.jar
COPY build/libs/post-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 11000
ENTRYPOINT ["java","-jar","/app.jar"]