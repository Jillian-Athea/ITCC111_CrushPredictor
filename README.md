# 💘 Crush Predictor App

A fun desktop application that predicts romantic compatibility between you and your crushes using a percentage-based system and displays cute messages based on the match score. Built with Java Swing, MySQL, and follows MVC architecture for clean design and scalability.

## 🧩 Project Features

- 🔐 User login system
- ❤️ Romantic match prediction (50–100%)
- 💬 Match result messages 
- 🗄️ Saves predictions to a MySQL database
- 🎨 Clean and styled GUI with custom fonts, icons, and hover effects
- ⚡ Event-driven logic and clear **MVC architecture**

---

## 🛠️ Requirements

To build and run this project, you'll need:

- ✅ **Java 8 or higher**
- ✅ **MySQL Server** installed and running
- ✅ **MySQL JDBC Driver** (`mysql-connector-java`)
- ✅ An IDE like **IntelliJ IDEA**, **Eclipse**, or **NetBeans**, or command-line tools
- ✅ Optional: **Git** for version control

---

## 🗂️ Folder Structure
src/
├── controller/
│ └── CrushController.java # Handles button events and business logic
├── model/
│ ├── CrushDAO.java # Handles match prediction and DB interaction
│ └── UserDAO.java # Handles user authentication
└── view/
├── CrushAppGUI.java # Main app interface
└── LoginGUI.java # Login screen

---

## You need:
- `assets/` – contains icons used in buttons
- `lib/` – optional folder for MySQL connector `.jar`

---

## 🗄️ Database Setup

This app uses a MySQL database named `crushpredictordb`.


1. Create the database:

CREATE DATABASE crushpredictordb;

2. Use the database:

USE crushpredictordb;

3. Create tables:

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE predictions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    your_name VARCHAR(100) NOT NULL,
    crush_name VARCHAR(100) NOT NULL,
    match_percentage INT NOT NULL,
    result_message VARCHAR(255) NOT NULL,
    prediction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

4. Insert at least one test user:

INSERT INTO users (username, password) VALUES ('testuser', 'password123');


---

## COMPILE
javac -cp ".;..\lib\mysql-connector-j-9.3.0.jar" view/LoginGUI.java view/CrushAppGUI.java model/CrushDAO.java model/UserDAO.java controller/CrushController.java


## RUN
java -cp ".;..\lib\mysql-connector-j-9.3.0.jar" view.LoginGUI
