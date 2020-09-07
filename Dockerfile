FROM maven:3.6-jdk-11-slim AS build
COPY . /home/src
WORKDIR /home/src
RUN ls /home/src
RUN mvn -f /home/src/pom.xml clean package

FROM openjdk:11-jre-slim
RUN mkdir /app
COPY --from=build /home/src/backend/target/backend*.jar /app/app.jar
RUN ls /app
EXPOSE 8080
CMD ["java" ,"-jar", "/app/app.jar"]