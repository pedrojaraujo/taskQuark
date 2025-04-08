FROM eclipse-temurin:23-jdk
LABEL authors="pedro.araujo@finnet.corp"

WORKDIR /app

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven

COPY . .
RUN mvn clean package -DskipTests

EXPOSE 8080

# Use o caminho exato para o JAR em vez de um padrão glob
CMD ["sh", "-c", "java -jar target/*.jar"]