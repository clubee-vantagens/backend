# cd C:\Users\aless\Documents\CluBee\authmicroservice
# docker build -t java:1  .
# mvn package -DskipTests

# Estágio de compilação
FROM maven:3.9.6 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn package -DskipTests -e

# Estágio final (runtime)
FROM openjdk:21
WORKDIR /app

# Recebendo variáveis do GitHub Actions
ARG SPRING_PROFILES_ACTIVE
ARG POSTGRES_URL
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

# Exportando como variáveis de ambiente no container
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE} \
    POSTGRES_URL=${POSTGRES_URL} \
    POSTGRES_USER=${POSTGRES_USER} \
    POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

# Copiando o .jar
COPY --from=builder /app/target/CluBee-Vantagens-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Executando com o perfil passado (e removendo os parâmetros fixos do RabbitMQ)
CMD ["java", "-jar", "CluBee-Vantagens-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
