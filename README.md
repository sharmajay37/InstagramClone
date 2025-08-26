# 📸 Instagram Clone App (Kotlin + Firebase)

An **Instagram-like Android application** built with **Kotlin** and **Firebase**, featuring authentication, user feeds, image uploads, search functionality, and user profiles.  
If a user is already logged in, they are redirected directly to the **Home Page**.

---

## 🚀 Features

- 🔑 **User Authentication** (Login & Signup with Firebase Email/Password)
- 🏠 **Home Feed** – View uploaded images
- 🔍 **Search** – Find users/content
- ⬆️ **Upload Images** – Share photos like Instagram
- 👤 **User Profile** – Manage and view user information
- 🔄 **Auto Login** – If already logged in, user is taken directly to Home Activity
- 📱 **Fragment-based Navigation** – Feed, Search, Upload, Profile handled via fragments

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **Backend:** Firebase Authentication & Firestore  
- **Database:** Firebase Firestore  
- **Storage:** Firebase Storage (for images)  
- **UI:** Android XML + Fragments  
- **IDE:** Android Studio  

---

## 📂 Project Structure

```

InstagramCloneApp/
│── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/instagramclone/
│   │   │   │   ├── activities/        # Login, Signup, Home Activities
│   │   │   │   ├── fragments/         # Feed, Search, Upload, Profile
│   │   │   │   ├── adapters/          # RecyclerView Adapters
│   │   │   │   ├── models/            # Data Models
│   │   │   │   └── utils/             # Helper Classes
│   │   │   └── res/                   # Layouts, Drawables, Values
│   └── build.gradle
└── README.md

````

---

## ⚙️ Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/InstagramCloneApp.git
````

2. **Open in Android Studio**

3. **Setup Firebase**

   * Create a Firebase project
   * Add your Android app package
   * Download `google-services.json` and place it in:

     ```
     app/
     ```
   * Enable **Authentication (Email/Password)** in Firebase Console
   * Enable **Firestore Database & Storage**

4. **Build & Run** the project on an emulator or physical device

---


## 🙌 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
