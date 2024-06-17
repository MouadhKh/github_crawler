FROM openjdk:17-jdk-slim
VOLUME /tmp

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk-slim
VOLUME /tmp

COPY target/github-crawler-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--organization","codecentric"]
