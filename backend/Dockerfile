FROM openjdk:11-jdk as BUILD
WORKDIR /build
COPY . /build
RUN ./mvnw clean package

FROM openjdk:11-jre
EXPOSE 8080
WORKDIR /app
COPY --from=BUILD /build/target/backend*.jar ./backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]