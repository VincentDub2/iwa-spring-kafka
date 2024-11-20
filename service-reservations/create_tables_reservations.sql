-- Table pour les réservations
CREATE TABLE IF NOT EXISTS Reservation (
    idReservation SERIAL PRIMARY KEY,
    idEmplacement BIGINT NOT NULL,
    idVoyageur BIGINT NOT NULL,
    dateArrive TIMESTAMP NOT NULL,
    dateDepart TIMESTAMP NOT NULL,
    prix DOUBLE PRECISION NOT NULL
);

-- Insertion de quelques valeurs d'exemple dans la table Reservation
INSERT INTO Reservation (idEmplacement, idVoyageur, dateArrive, dateDepart, prix) VALUES
(1, 100, '2024-11-20 14:00:00', '2024-11-22 11:00:00', 150.00),
(2, 101, '2024-12-05 15:00:00', '2024-12-10 10:00:00', 300.00),
(3, 102, '2025-01-03 12:00:00', '2025-01-07 12:00:00', 400.00);