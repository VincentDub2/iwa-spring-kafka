-- Création de la table Emplacement
CREATE TABLE Emplacement (
                             id_emplacement BIGSERIAL PRIMARY KEY,
                             id_hote BIGINT NOT NULL,                -- Correspond à idHote dans Java
                             nom VARCHAR(255) NOT NULL,             -- Correspond à nom dans Java
                             adresse VARCHAR(255) NOT NULL,         -- Correspond à adresse dans Java
                             description TEXT,                      -- Correspond à description dans Java
                             latitude DOUBLE PRECISION NOT NULL,    -- Correspond à latitude dans Java
                             longitude DOUBLE PRECISION NOT NULL,   -- Correspond à longitude dans Java
                             prix_par_nuit NUMERIC(10, 2) NOT NULL, -- Correspond à prixParNuit dans Java
                             image BYTEA                            -- Correspond à image dans Java
);

-- Création de la table Dispo (disponibilités liées à un emplacement)
CREATE TABLE Dispo (
                       id_dispo BIGSERIAL PRIMARY KEY,
                       id_emplacement BIGINT NOT NULL,        -- Clé étrangère vers Emplacement
                       date_debut DATE NOT NULL,              -- Correspond à dateDebut dans Java
                       date_fin DATE NOT NULL,                -- Correspond à dateFin dans Java
                       FOREIGN KEY (id_emplacement) REFERENCES Emplacement(id_emplacement) ON DELETE CASCADE
);

-- Création de la table Emplacement_Commodites (commodités liées à un emplacement)
CREATE TABLE Emplacement_Commodites (
                                        id_emplacement BIGINT NOT NULL,        -- Clé étrangère vers Emplacement
                                        commodite VARCHAR(255) NOT NULL,       -- Correspond à commodités dans Java
                                        FOREIGN KEY (id_emplacement) REFERENCES Emplacement(id_emplacement) ON DELETE CASCADE
);

-- Insertion de quelques données de test pour Emplacement
INSERT INTO Emplacement (id_hote, nom, adresse, description, latitude, longitude, prix_par_nuit, image)
VALUES
    (1, 'Chalet en montagne', '123 Rue des Alpes, Annecy', 'Chalet cosy avec vue sur les montagnes.', 45.8995, 6.1286, 120.00, NULL),
    (2, 'Appartement en bord de mer', '45 Quai de la Mer, Nice', 'Appartement lumineux avec accès direct à la plage.', 43.6954, 7.2656, 150.00, NULL);

-- Insertion de données de test pour Dispo
INSERT INTO Dispo (id_emplacement, date_debut, date_fin)
VALUES
    (1, '2024-12-01', '2024-12-10'),
    (1, '2024-12-15', '2024-12-20'),
    (2, '2024-11-20', '2024-11-30');

-- Insertion de données de test pour Emplacement_Commodites
INSERT INTO Emplacement_Commodites (id_emplacement, commodite)
VALUES
    (1, 'WiFi'),
    (1, 'Cheminée'),
    (2, 'Climatisation'),
    (2, 'Piscine'),
    (2, 'Parking gratuit');
