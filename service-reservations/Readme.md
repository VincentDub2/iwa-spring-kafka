# Reconstruire votre application avec Gradle

```bash
./gradlew clean build
./gradlew clean build -x test    
```

# Arreter et Supprimer l’ancien conteneur Docker
    
```bash
docker stop reservation-service
docker rm reservation-service
```

# Construire une nouvelle image Docker
```bash
docker build -t reservation-service .
```

# Lancer un nouveau conteneur Docker
```bash 
docker run --name reservation-service --network my-network -p 8085:8085 reservation-service
```

# Commandes utiles psql

```bash
\c dbname	Change la base de données active.
\l	Liste toutes les bases de données disponibles.
\dt	Liste toutes les tables de la base de données active.
\du	Liste tous les rôles de la base de données.
\q	Quitte PostgreSQL.
```