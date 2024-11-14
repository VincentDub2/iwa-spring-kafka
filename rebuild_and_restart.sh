#!/bin/bash

# Vérifie si un service est fourni en argument
if [ -z "$1" ]; then
  echo "Usage: $0 <nom_du_service> [all]"
  echo "Utilisez 'all' pour reconstruire et relancer tous les services."
  exit 1
fi

# Récupère le nom du service
SERVICE_NAME=$1

# Fonction pour rebuild et restart un service spécifique
rebuild_service() {
  echo "Reconstruction du service $SERVICE_NAME..."
  docker-compose build $SERVICE_NAME

  echo "Relance du service $SERVICE_NAME..."
  docker-compose up -d $SERVICE_NAME
}

# Fonction pour rebuild et restart tous les services
rebuild_all_services() {
  echo "Reconstruction de tous les services..."
  docker-compose build

  echo "Relance de tous les services..."
  docker-compose up -d
}

# Vérifie si l'argument est 'all' ou un nom de service
if [ "$SERVICE_NAME" == "all" ]; then
  rebuild_all_services
else
  rebuild_service
fi

# Affiche le statut des conteneurs après la relance
echo "État des conteneurs Docker :"
docker-compose ps
