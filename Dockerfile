FROM maven:3-amazoncorretto-21 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM amazoncorretto:21

COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]