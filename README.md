# 📊 Income & Expense Tracker App

Track your personal finances automatically through SMS and manual entry — works offline with secure Firebase sync.

---

## 🚀 Features

- 📥 Parses SMS to track income & expenses
- ✍️ Manual transaction entry
- 📶 Works offline using Room DB
- ☁️ Syncs with Firebase Firestore
- 🔐 Firebase Authentication
- 📆 Monthly summaries & category reports
- 📊 Clean architecture with MVI + Jetpack Compose

---

## 🧱 Architecture Overview

This project follows **Clean Architecture** + **MVI (Model-View-Intent)** pattern.

app/
├── presentation/ # Jetpack Compose UI + MVI ViewModel
├── domain/ # UseCases, Entities, Repository interfaces
├── data/ # Firebase, Room, SMS parsing, Mappers
├── di/ # Hilt modules
├── utils/ # Constants, extensions


### 🔁 MVI Flow

User Intent
↓
ViewModel
↓
UseCase → Repository
↓
UIState → View


---

## 🛠️ Tech Stack

| Layer         | Tech                                                                 |
|---------------|----------------------------------------------------------------------|
| UI            | Jetpack Compose, Material3                                           |
| Architecture  | MVI + Clean Architecture                                             |
| Local DB      | Room                                                                 |
| Remote DB     | Firebase Firestore                                                   |
| Auth          | Firebase Auth (email/password)                                       |
| Background    | WorkManager (for sync)                                               |
| Dependency Injection | Hilt                                                          |
| Language      | Kotlin (100%)                                                        |

---

## 🔐 Firebase Structure

