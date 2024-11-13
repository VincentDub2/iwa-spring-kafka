-- Création de la table Conversation
CREATE TABLE Conversation (
    id BIGSERIAL PRIMARY KEY,
    person_one_id BIGINT NOT NULL,  -- Correspond à personOneId dans Java
    person_two_id BIGINT NOT NULL   -- Correspond à personTwoId dans Java
);

-- Création de la table Message
CREATE TABLE Message (
    id BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,      -- L'expéditeur du message
    contenu TEXT NOT NULL,
    date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (conversation_id) REFERENCES Conversation(id) ON DELETE CASCADE
);

-- Insertion de quelques données de test pour Conversation
INSERT INTO Conversation (person_one_id, person_two_id) VALUES (1, 2);
INSERT INTO Conversation (person_one_id, person_two_id) VALUES (3, 4);

-- Insertion de quelques données de test pour Message
INSERT INTO Message (conversation_id, sender_id, contenu, date) VALUES (1, 1, 'Bonjour, comment allez-vous?', '2024-11-13 10:00:00');
INSERT INTO Message (conversation_id, sender_id, contenu, date) VALUES (1, 2, 'Très bien, merci. Et vous?', '2024-11-13 10:05:00');
INSERT INTO Message (conversation_id, sender_id, contenu, date) VALUES (2, 3, 'Salut, ça fait longtemps!', '2024-11-13 10:10:00');
