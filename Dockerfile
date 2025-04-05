# cd C:\Users\aless\Documents\CluBee\authmicroservice
# docker build -t java:1  .
# mvn package -DskipTests

# Estágio de compilação
FROM maven:3.9.6 AS builder
WORKDIR /app
COPY authmicroservice/pom.xml .
RUN mvn dependency:go-offline
COPY authmicroservice/src /app/src/
RUN mvn package -DskipTests -e

# Estágio de construção da imagem
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/CluBee-Vantagens-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "CluBee-Vantagens-0.0.1-SNAPSHOT.jar", "--spring.rabbitmq.addresses=amqps://drxyafjf:kANMRmSVr3eY370hZpt4iQ4x7zDxA3nJ@jackal.rmq.cloudamqp.com:5671/drxyafjf", "--spring.rabbitmq.ssl.enabled=true"]