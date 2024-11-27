#!/bin/bash

# Couleurs
RED="\033[0;31m"
GREEN="\033[0;32m"
YELLOW="\033[0;33m"
CYAN="\033[0;36m"
NC="\033[0m" # Pas de couleur

# Liste des microservices
MICROSERVICES=(
  "biv_quack_ms_users"
  "biv_quack_ms_api_gateway"
  "biv_quack_ms_eureka_discovery"
  "biv_quack_ms_reservations"
  "biv_quack_ms_emplacements"
  "biv_quack_ms_evaluation"
  "biv_quack_ms_notification"
)

# Parcours des microservices
for SERVICE in "${MICROSERVICES[@]}"; do
  if [ -d "$SERVICE" ]; then
    echo -e "${CYAN}========== Traitement de $SERVICE ==========${NC}"
    cd "$SERVICE"

    # Vérifier si le dossier est vide
    if [ -z "$(ls -A .)" ]; then
      echo -e "${YELLOW}Le dossier $SERVICE est vide. Initialisation des sous-modules...${NC}"
      git submodule init
      git submodule update
    fi

    # Vérifier si le dossier est un dépôt Git
    if git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
      git checkout main
      # Essayer de faire un git pull
      git pull
      if [ $? -ne 0 ]; then
        # Si git pull échoue, faire un git checkout main et retenter
        echo -e "${YELLOW}Problème détecté dans $SERVICE. Passage à la branche main...${NC}"
        git checkout main
        if [ $? -eq 0 ]; then
          echo -e "${GREEN}Branch main récupérée avec succès dans $SERVICE. Retentative du pull...${NC}"
          git pull
        else
          echo -e "${RED}Impossible de passer à la branche main dans $SERVICE.${NC}"
        fi
      fi
    else
      echo -e "${YELLOW}$SERVICE n'est pas un dépôt Git.${NC}"
    fi
    cd ..
  else
    echo -e "${RED}Dossier $SERVICE non trouvé.${NC}"
  fi
done

echo -e "${GREEN}Traitement terminé pour tous les microservices.${NC}"
