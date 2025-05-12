FROM maven:3.9.6 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn package -DskipTests -e

FROM openjdk:21
WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=builder /app/target/CluBee-Vantagens-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["sh", "-c", "java -jar CluBee-Vantagens-0.0.1-SNAPSHOT.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]