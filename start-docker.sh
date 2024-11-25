#!/bin/bash

# Nom du fichier docker-compose
COMPOSE_FILE="docker-compose.yml"

# Vérification de l'existence du fichier docker-compose
if [ ! -f "$COMPOSE_FILE" ]; then
    echo "Erreur : Le fichier $COMPOSE_FILE est introuvable dans le répertoire actuel."
    exit 1
fi

echo "Arrêt et suppression des conteneurs existants..."
docker-compose -f $COMPOSE_FILE down

# Suppression de toutes les images Docker
echo "Suppression de toutes les images Docker..."
docker image prune -a -f

echo "Récupération des dernières versions des images..."
docker-compose -f $COMPOSE_FILE pull

echo "Lancement des services avec reconstruction des conteneurs..."
docker-compose -f $COMPOSE_FILE up --build -d

# Fonction pour vérifier l'état des services
check_services() {
    echo "Vérification de l'état des services..."
    SERVICES=($(docker-compose ps --services))
    for service in "${SERVICES[@]}"; do
        STATUS=$(docker inspect -f '{{.State.Health.Status}}' "$(docker-compose ps -q $service)" 2>/dev/null || echo "unknown")
        echo "Service $service : $STATUS"
    done
}

# Pause pour laisser le temps aux services de démarrer
echo "Attente de 10 secondes pour le démarrage des services..."
sleep 10

# Vérification des services
check_services

echo "Tous les services ont été lancés avec les dernières images. Utilisez 'docker-compose logs -f' pour voir les logs."
