-- 1. UTENTI 
-- La password per tutti è "password"
INSERT INTO users (id, nome, cognome, email) VALUES (1, 'Mario', 'Admin', 'admin@wantedstreetfood.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (1, 'admin', '$2a$10$fuKb3Sr0A7TcqhwasLGkM.t9IScbiBLGzKXViZpneoEQNe8zZ.xA6', 'ADMIN', 1);

INSERT INTO users (id, nome, cognome, email) VALUES (2, 'Leonardo', 'Cliente', 'cliente@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (2, 'user', '$2a$10$fuKb3Sr0A7TcqhwasLGkM.t9IScbiBLGzKXViZpneoEQNe8zZ.xA6', 'DEFAULT', 2);

INSERT INTO users (id, nome, cognome, email) VALUES (3, 'Giulia', 'Bianchi', 'giulia.bianchi@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (3, 'giulia', '$2a$10$fuKb3Sr0A7TcqhwasLGkM.t9IScbiBLGzKXViZpneoEQNe8zZ.xA6', 'DEFAULT', 3);

INSERT INTO users (id, nome, cognome, email) VALUES (4, 'Marco', 'Verdi', 'marco.verdi@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (4, 'marco', '$2a$10$fuKb3Sr0A7TcqhwasLGkM.t9IScbiBLGzKXViZpneoEQNe8zZ.xA6', 'DEFAULT', 4);

-- 2. CATEGORIE
INSERT INTO categoria (id, nome) VALUES (1, 'Primi');
INSERT INTO categoria (id, nome) VALUES (2, 'Panini');
INSERT INTO categoria (id, nome) VALUES (3, 'Fritti');
INSERT INTO categoria (id, nome) VALUES (4, 'Dolci');

-- 3. PIATTI
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (1, 'Supplì Classico', 'Riso, sugo di carne, mozzarella filante', 3.0, true, 3);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (2, 'Pasta Cacio e Pepe', 'Tonnarelli con Pecorino Romano e pepe nero', 8.5, true, 1);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (3, 'Porchetta Romana', 'Panino con porchetta di Ariccia croccante', 7.0, true, 2);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (4, 'Trapizzino alla Coda', 'Triangolo di pizza bianca ripieno di coda alla vaccinara', 6.5, true, 2);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (5, 'Fiori di Zucca Fritti', 'Fiori di zucca in pastella con acciuga e mozzarella', 5.0, true, 3);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (6, 'Bombolone alla Crema', 'Bombolone fritto ripieno di crema pasticcera', 3.5, true, 4);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (7, 'Amatriciana', 'Rigatoni con guanciale, pomodoro e pecorino', 9.0, true, 1);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (8, 'Pajata alla Romana', 'Piatto della tradizione, momentaneamente in pausa', 8.0, false, 1);
INSERT INTO piatto (id, nome, descrizione, prezzo, disponibile, categoria_id) VALUES (9, 'Panino al Lampredotto', 'Specialità fiorentina, disponibile dalla prossima tappa', 6.0, false, 2);

-- 4. TAPPE (I mercati per l'allestimento della tenda)
INSERT INTO tappa (id, nome_mercato, indirizzo, giorno_settimana) VALUES (1, 'Mauerpark', 'Gleimstraße 55, 10437 Berlin', 'Domenica');
INSERT INTO tappa (id, nome_mercato, indirizzo, giorno_settimana) VALUES (2, 'Markthalle Neun', 'Eisenbahnstraße 42, 10997 Berlin', 'Giovedì');

-- Diciamo a Postgres che i prossimi ID generati automaticamente dal sito devono partire da 100
ALTER SEQUENCE users_seq RESTART WITH 100;
ALTER SEQUENCE credentials_seq RESTART WITH 100;
ALTER SEQUENCE categoria_seq RESTART WITH 100;
ALTER SEQUENCE piatto_seq RESTART WITH 100;
ALTER SEQUENCE tappa_seq RESTART WITH 100;

-- 5. RECENSIONI (autore_id fa riferimento a users.id) - scritte da 3 utenti diversi già registrati
INSERT INTO recensione (id, stelle, commento, autore_id) VALUES (1, 5, 'Il miglior street food di Roma, cacio e pepe pazzesca!', 2);
INSERT INTO recensione (id, stelle, commento, autore_id) VALUES (2, 4, 'Ottima la porchetta, tornero'' sicuramente.', 2);
INSERT INTO recensione (id, stelle, commento, autore_id) VALUES (3, 5, 'Il trapizzino alla coda è pura poesia, consigliatissimo!', 3);
INSERT INTO recensione (id, stelle, commento, autore_id) VALUES (4, 3, 'Buono ma un po'' caro rispetto ad altri chioschi.', 4);
ALTER SEQUENCE recensione_seq RESTART WITH 100;