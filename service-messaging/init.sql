-- Création de la table Conversation
CREATE TABLE IF NOT EXISTS Conversation (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL
);

-- Création de la table Message
CREATE TABLE IF NOT EXISTS Message (
    id BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    contenu TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES Conversation(id) ON DELETE CASCADE
);

-- Insertion de données d'exemple dans Conversation
INSERT INTO Conversation (sender_id, receiver_id) VALUES
    (1, 2),
    (3, 4);

-- Insertion de données d'exemple dans Message
INSERT INTO Message (conversation_id, sender_id, receiver_id, contenu, date) VALUES
    (1, 1, 2, 'Bonjour! Comment ça va?', NOW()),
    (1, 2, 1, 'Ça va bien, merci! Et toi?', NOW()),
    (2, 3, 4, 'Salut! On se voit demain ?', NOW());
