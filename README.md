# IOU App

A starter Android application built with Jetpack Compose and Room that manages a simple list of users. The home screen shows all saved users and offers an action button to create a new entry. Adding or editing a user opens a form screen where the user's name can be stored in the local Room database.

## Features

- Jetpack Compose UI with Material 3 styling.
- Room persistence layer for storing user names locally.
- Navigation between list, add, and edit screens.
- Inline validation with snackbar feedback for empty names.

## Getting started

1. Ensure you have Android Studio Jellyfish (or newer) with the Android SDK 34 installed.
2. Open this directory in Android Studio.
3. Sync the Gradle project when prompted.
4. Run the `app` configuration on an emulator or physical device.

### Gradle wrapper bootstrap

The `gradle-wrapper.jar` binary cannot be stored directly in this repository. Instead, its Base64 representation lives in
`gradle/wrapper/gradle-wrapper.jar.base64`. Running `./gradlew` (or `gradlew.bat` on Windows) automatically decodes the file back
into the wrapper JAR when it is missing. If decoding fails or you want to recreate the wrapper from scratch, install Gradle
locally and execute `gradle wrapper` to regenerate the artifacts.

## Project structure

- `app/src/main/java/com/example/iouapp` – Kotlin source files.
  - `data` – Room entity, DAO, database, and repository.
  - `ui` – Compose screens for list and forms.
  - `ui/theme` – Theme definitions.
  - `viewmodel` – `UserViewModel` that coordinates UI actions.
- `app/src/main/res` – Android resources.
- `app/build.gradle.kts` – Module build configuration using Compose and Room.

The project is designed to be extendable so that future per-user features can be implemented on the edit screen or via new destinations.
