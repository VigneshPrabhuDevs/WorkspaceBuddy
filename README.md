# WorkspaceBuddy

WorkspaceBuddy is a modern, baseline Task Manager Android application built with **Clean Architecture**, **MVI (Model-View-Intent)**, and the latest Jetpack libraries. It features a responsive, adaptive UI and exposes business logic to AI agents via the **Android AppFunctions** framework.

## 🚀 Features

- **Task Management**: Create, toggle, and delete tasks with ease.
- **Priority System**: Organize work using High, Medium, and Low priority tiers.
- **Due Dates**: Integrated date selection for tracking deadlines.
- **Adaptive UI**: Responsive layouts using Material 3 Adaptive components that scale from compact phones to expanded tablets/foldables.
- **MVI Architecture**: Predictable state management using Kotlin Flows and ViewModels.
- **Local Persistence**: Robust storage using Room Database.
- **AppFunctions Integration**: Exposes core functionality (like task creation) to system-level AI agents and voice assistants.

## 🛠 Tech Stack

- **UI**: Jetpack Compose, Material Design 3
- **Navigation**: Jetpack Navigation 3 (State-driven)
- **Architecture**: Clean Architecture (Domain, Data, Presentation)
- **Concurrency**: Kotlin Coroutines & Flow
- **Persistence**: Room Database
- **Dependency Management**: Gradle Version Catalog (libs.versions.toml)
- **AI Integration**: androidx.appfunctions

---

## 🏗 Architecture Overview

The project follows Clean Architecture principles to ensure scalability and testability:

1.  **Domain Layer**: Contains the core business logic, including the `Task` model, `Priority` enum, and specialized Use Cases (`AddTaskUseCase`, `GetTasksUseCase`, etc.). This layer is independent of any Android framework.
2.  **Data Layer**: Handles persistence and data mapping. Includes the Room `TaskEntity`, `TaskDao`, and the `TaskRepositoryImpl`.
3.  **Presentation Layer (MVI)**: 
    *   **ViewState**: A single immutable object representing the UI state.
    *   **Intent**: User actions captured and sent to the ViewModel.
    *   **ViewModel**: Processes intents, interacts with use cases, and emits new ViewStates.

---

## 📱 Getting Started

### Prerequisites

- Android Studio Meerkat (or newer)
- Android SDK 37 (Android 16 Preview)
- An Android device or emulator running API 37 or newer for full AppFunctions support.

### Installation

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Sync Gradle and build the project.

---

## 🧪 Testing AppFunctions via ADB

WorkspaceBuddy integrates with the **Android AppFunctions** framework. You can trigger the task creation logic headlessly (without opening the UI) using ADB. This simulates an AI agent or voice assistant invoking your app's tools.

### Function Details
- **Package**: `com.vpdevs.workspacebuddy`
- **Function ID**: `com.vpdevs.workspacebuddy.appfunctions.WorkspaceAppFunctions#createTask`
- **Parameters**: 
    - `title` (String, Required)
    - `description` (String, Optional)
    - `priority` (String, Optional - "HIGH", "MEDIUM", "LOW")
    - `dueDateIso` (String, Optional - ISO format YYYY-MM-DD)

### ADB Command

Use the following command to invoke the function via the system terminal:

```bash
adb shell cmd appfunctions invoke-app-function \
  --package com.vpdevs.workspacebuddy \
  --function-id com.vpdevs.workspacebuddy.appfunctions.WorkspaceAppFunctions#createTask \
  --parameters '{"title": "Check server logs", "priority": "HIGH", "description": "Investigate errors in production"}'
```

**Note**: Ensure the device is unlocked and the app is installed. The result of the function call will be printed directly in your terminal.

---

## 📁 Project Structure

```text
app/src/main/java/com/vpdevs/workspacebuddy/
├── appfunctions/     # AppFunction definitions and mapping
├── data/             # Local database, entities, and repository implementations
├── domain/           # Core models and business logic (Use Cases)
├── presentation/     # MVI components (ViewModels, UI, Navigation)
└── ui/theme/         # Material 3 color schemes and typography
```

## ⚖️ License

Distributed under the MIT License. See `LICENSE` for more information.
