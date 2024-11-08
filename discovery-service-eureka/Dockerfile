# 1. Étape de build
# Utilisez une image Gradle pour construire l'application
FROM gradle:8.8-jdk21 AS build

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez les fichiers du projet dans le conteneur
COPY . .

# Lancez la compilation de l’application et la création de l'artefact JAR
RUN gradle bootJar --no-daemon

# 2. Étape d'exécution
# Utilisez une image JDK légère pour exécuter le microservice
FROM openjdk:21-jdk-slim

# Définissez le répertoire de travail dans le conteneur d'exécution
WORKDIR /app

# Copiez le fichier JAR généré de l'étape précédente
COPY --from=build /app/build/libs/*.jar app.jar

# Exposez le port sur lequel le microservice écoutera
EXPOSE 8080

# Commande de démarrage du microservice
ENTRYPOINT ["java", "-jar", "app.jar"]
