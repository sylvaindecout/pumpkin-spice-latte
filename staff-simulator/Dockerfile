FROM eclipse-temurin:21-jre-alpine

RUN apk --no-cache add curl

RUN mkdir -p /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
