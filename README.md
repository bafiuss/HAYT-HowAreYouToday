# HAYT - How Are You Today? 🌿

Benvenuti in **HAYT**!
Una piattaforma web pensata per supportare il percorso terapeutico tra paziente e psicoterapeuta, con strumenti semplici, pratici e in un unico posto.

## 🎓 Contesto accademico

Questo lavoro è stato realizzato nell'ambito della **tesi triennale in Informatica** presso l'**Universita degli Studi di Salerno**.

## ✨ Funzionalita principali

- 🔐 Autenticazione con ruoli paziente/psicoterapeuta
- 📖 Diario personale con analisi del sentiment
- 💬 Chat in tempo reale tra paziente e psicoterapeuta
- 📅 Gestione appuntamenti con calendario
- ❓ Questionario periodico
- 🧘 Tips personalizzati in base all'andamento del diario

## 🧱 Stack tecnologico

- **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, WebSocket
- **Database:** MySQL
- **Servizio NLP:** Python, Flask, Transformers, Torch

---

## 🚀 Guida utente (setup locale)

Di seguito sono riportati i passaggi consigliati per avviare il progetto in locale.

### 1) Prerequisiti

Prima di procedere, verificare di avere installato:

- Java 17
- MySQL (attivo in locale)
- Conda (consigliato, per utilizzare il file `.yml`)

### 2) Clone della repository

```bash
git clone https://github.com/bafiuss/HAYT-HowAreYouToday.git
cd HAYT-HowAreYouToday
```

### 3) Configurazione database MySQL

Parametri di default:

- URL: `jdbc:mysql://localhost:3306/hayt?createDatabaseIfNotExist=true`
- Username: `root`
- Password: `root`

È possibile mantenere questi valori oppure modificarli in `src/main/resources/application.properties`.

### 4) Installazione pacchetti Python dal file `.yml`

Dalla root del progetto:

```bash
conda env create -f environment.yml
conda activate hayt
```

### 5) Avvio del servizio Python NLP

Con l'ambiente Conda attivo:

```bash
cd src/nlp-service
python app.py
```

Il servizio NLP sara disponibile su:

- `http://localhost:5000/analyze`

### 6) Avvio applicazione Spring Boot

Aprire un nuovo terminale nella root del progetto e avviare il backend.

```bash
mvn spring-boot:run
```

Infine, è possibile accedere all'applicazione tramite browser all'indirizzo `http://localhost:8080`.

---
