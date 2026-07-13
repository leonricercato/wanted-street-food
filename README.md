# Wanted Street Food — Progetto Personale SIW

Sistema informativo web per la gestione di un'attività di street food itinerante:
menù dei piatti, calendario delle tappe (mercati), recensioni dei clienti.

## Stack tecnologico

- **Backend:** Spring Boot 4.1.0 (Java 17)
- **Persistenza:** JPA / Hibernate
- **Database:** PostgreSQL
- **Frontend:** Thymeleaf (server-side) + React (componente recensioni, lato client)
- **Sicurezza:** Spring Security (autenticazione form-based, autorizzazione basata su ruoli)

## Modello di dominio

### Entità

| Entità | Campi principali | Relazioni |
|---|---|---|
| **User** | id, nome, cognome, email | 1–1 con Credentials |
| **Credentials** | id, username, password (BCrypt), role (`ADMIN`/`DEFAULT`) | 1–1 con User |
| **Categoria** | id, nome | 1–N con Piatto |
| **Piatto** | id, nome, descrizione, prezzo, fotoUrl, disponibile | N–1 con Categoria |
| **Tappa** | id, nomeMercato, indirizzo, giornoSettimana, fotoUrl | — |
| **Recensione** | id, stelle (1-5), commento | N–1 con User (autore) |

### Note di progettazione

- **User / Credentials separati**: dati anagrafici (User) e dati di accesso (Credentials)
  sono tenuti distinti per rispettare la separazione delle responsabilità — Credentials
  gestisce solo autenticazione/autorizzazione, User rappresenta la persona.
- **Categoria → Piatto (OneToMany)**: permette di raggruppare il menù per sezioni
  (Primi, Panini, Fritti, Dolci) senza duplicare dati.
- **Piatto.disponibile**: booleano che permette all'admin di nascondere temporaneamente
  un piatto dal menù pubblico senza cancellarlo (es. ingrediente esaurito), mantenendo
  storico e relazioni intatte.
- **Recensione → User (autore)**: ogni recensione è sempre associata all'utente
  autenticato che l'ha scritta (mai inserita manualmente dal client), per garantire che
  un utente possa modificare/eliminare solo le proprie recensioni.

## Ruoli e autorizzazioni

- **Anonimo (non loggato)**: può consultare menù, tappe e recensioni. Non può scrivere,
  modificare o eliminare nulla.
- **USER (registrato)**: oltre a tutto quanto sopra, può scrivere una recensione e
  modificare/eliminare **solo le proprie**.
- **ADMIN**: gestione completa di Piatto e Tappa (creazione, modifica, eliminazione,
  attivazione/disattivazione disponibilità); può inoltre moderare (eliminare) qualsiasi
  recensione.

La distinzione tra ruoli è imposta sia lato interfaccia (Thymeleaf `sec:authorize`,
componente React che riceve il ruolo dal server) sia — soprattutto — **lato server**,
dove ogni endpoint di scrittura ricontrolla autenticazione/proprietà/ruolo indipendentemente
da cosa mostra l'interfaccia.

## Casi d'uso implementati

**Creazione (Create)**
1. L'amministratore aggiunge un nuovo piatto al menù, assegnandolo a una categoria.
2. L'amministratore aggiunge una nuova tappa (mercato).
3. Un utente registrato scrive una recensione.

**Aggiornamento (Update)**
4. L'amministratore modifica i dati di un piatto esistente.
5. L'amministratore modifica i dati di una tappa esistente.
6. L'amministratore attiva/disattiva la disponibilità di un piatto senza eliminarlo.
7. Un utente registrato modifica il testo/voto della propria recensione.

**Cancellazione (Delete)**
8. L'amministratore elimina un piatto.
9. L'amministratore elimina una tappa.
10. Un utente registrato elimina la propria recensione (l'amministratore può eliminare
    qualsiasi recensione, come moderazione).

**Lettura (Read)**
11. Chiunque visualizza il menù, raggruppato per categoria.
12. Chiunque visualizza l'elenco delle tappe.
13. Chiunque visualizza le recensioni dei clienti (renderizzate lato client in React,
    tramite endpoint REST `/api/recensioni`).

*(Nota: autenticazione e registrazione, pur implementate, non sono conteggiate come
casi d'uso, come indicato nelle specifiche.)*

## Componente React

Il file `templates/recensioni.html` include un componente React (caricato via CDN,
senza build tool) che:
- recupera le recensioni da `GET /api/recensioni` (endpoint REST che restituisce JSON);
- le renderizza lato client usando `useState`/`useEffect`;
- permette all'autore di una recensione di modificarla/eliminarla inline, chiamando
  rispettivamente `PUT` e `DELETE` su `/api/recensioni/{id}`.

## Architettura a livelli

- **Repository** (`repository/`): interfacce `CrudRepository` per l'accesso ai dati.
- **Service** (`service/`): logica applicativa, metodi di scrittura annotati
  `@Transactional`.
- **Controller** (`controller/`): gestione delle richieste HTTP (sia MVC verso
  Thymeleaf, sia REST verso il componente React), delega la logica ai service.

## Credenziali di test (dati precaricati da `import.sql`)

| Username | Password | Ruolo |
|---|---|---|
| admin | password | ADMIN |
| user | password | DEFAULT |
| giulia | password | DEFAULT |
| marco | password | DEFAULT |

## Malfunzionamenti noti

*(da compilare prima dell'invio, se presenti)*
