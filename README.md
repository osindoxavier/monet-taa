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

/users/{userId}
/users/{userId}/transactions/{transactionId}

### Transaction Document Sample

```json
{
  "id": "uuid",
  "amount": 1200.0,
  "type": "INCOME",
  "category": "Salary",
  "source": "SMS",
  "timestamp": 1721841124,
  "description": "Monthly salary"
}

🔒 Firestore Security Rules
js
Copy
Edit
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      match /transactions/{transactionId} {
        allow read, write: if request.auth.uid == userId;
      }
    }
  }
}
🧪 Test Cases
✅ Login and register

✅ Read SMS and auto-generate transaction

✅ Add transaction manually

✅ View monthly summary and reports

✅ Offline storage

✅ Firebase sync (manual and periodic)

📸 UI Mockups



Clone the repo:

bash
Copy
Edit
git clone https://github.com/osindoxavier/monet-taa.git
Add your google-services.json for Firebase

Build and run on Android Studio (min SDK 24)

💡 Future Features

🔄 Two-way sync with Firebase

🌍 Multi-language support

📈 Analytics and budget forecasts

🔔 Smart reminders

📧 Contact
Maintained by Francis Xavier
If you like this project, ⭐️ it and contribute!

