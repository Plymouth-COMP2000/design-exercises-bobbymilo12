# Restaurant Reservation App (Android)
## Overview

- This project is an Android application developed as part of the COMP2000 module coursework.

- The application supports two user roles:

### Guest users

- Browse menu items

- Create, view, edit, and delete their own reservations

- Receive notifications when reservations are created, updated, or deleted (configurable)

### Staff users

- View all reservations

- Cancel guest reservations

- Add, edit, and delete menu items

- Receive notifications when reservations are modified by guests

Role-based behaviour is handled internally within the application for assessment purposes.
No external authentication service or backend API is required to run or evaluate the app.

## Technologies Used

- Android Studio

- Java

- SQLite (via SQLiteOpenHelper)

- Material Design Components

- Android Notifications API

- RecyclerView for dynamic lists

- Data Persistence

- All application data is stored locally using SQLite.

### The database is used to persist:

- Reservations

- Menu items

- User accounts (guest and staff)

- CRUD (Create, Read, Update, Delete) operations are implemented using helper and adapter classes to separate concerns between UI and data handling.

### Notifications

- The app uses the Android Notifications API to notify users when reservation-related actions occur:

- Reservation created

- Reservation updated

- Reservation deleted (by guest or staff)

- Guests can control notification preferences from the Manage Reservations screen, while staff notifications are always enabled.

### Third-Party / External Resources

- Android Material Components (UI elements)

- AndroidX Libraries

- RecyclerView

- ConstraintLayout

### Core & AppCompat libraries

- Glide (for loading menu item images via URLs)

- No external backend services or cloud APIs are used.

## How to Run the App

- Clone the repository

- Open the project in Android Studio

- Allow Gradle to sync

- Run the app on an emulator or physical Android device (Android 8.0+ recommended)

## Notes for Assessment

- The app is fully self-contained

- No network connection is required

- All data is stored locally

- Role behaviour is simulated for coursework requirements
