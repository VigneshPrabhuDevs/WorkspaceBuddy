# Project Plan

Update WorkspaceBuddy to include "Due Date" support in the UI. This involves displaying the due date in the task list and providing an input field (Date Picker) in the task creation forms.

## Project Brief

# Project Brief: WorkspaceBuddy Refinement - Due Date Support

WorkspaceBuddy needs to be updated to include visibility and input for the "Due Date" field which is already present in the domain model but missing from the UI.

## Features to Add
- **Display Due Date**: Show the due date in the task list items.
- **Input Due Date**: Allow users to select or enter a due date when creating a task in both the mobile dialog and the tablet side panel.
- **Format Consistency**: Ensure the date is handled as an ISO string in the backend and formatted nicely in the UI.

## High-Level Tech Stack
- Jetpack Compose (Material 3 DatePicker)
- MVI Pattern (Update Intents and State)
- Clean Architecture (Ensure flow from UI to Room)

## Implementation Steps

### Task_1_Core_Domain_Data: Define the core business logic and data persistence layer. Create the Task domain model, Priority enum, TaskRepository interface, Room TaskEntity, TaskDao, and the WorkspaceDatabase.
- **Status:** COMPLETED
- **Updates:** Successfully implemented Domain and Data layers. Created Task and Priority models, TaskRepository interface, and Use Cases (Add, Get, Delete, Toggle). Implemented Room components including TaskEntity, TaskDao, WorkspaceDatabase, and TaskRepositoryImpl with mappers. Upgraded SDK to 37 for library compatibility. Project builds successfully.
- **Acceptance Criteria:**
  - Domain models and Repository interface defined
  - Room database, Entity, and DAO implemented
  - Repository implementation bridges data and domain layers
  - Build passes

### Task_2_MVI_Logic_ViewModel: Implement the MVI (Model-View-Intent) pattern. Define the TaskContract containing ViewState, Intent, and SideEffect. Create the TaskViewModel to manage state transitions and coordinate with the Repository.
- **Status:** COMPLETED
- **Updates:** Implemented MVI architecture. Defined TaskContract with ViewState, UiIntent, and SideEffect. Created TaskViewModel using StateFlow for state management and Channel for side effects. Integrated Use Cases for task CRUD and filtering. Verified with a successful build.
- **Acceptance Criteria:**
  - TaskContract defines exhaustive states and intents
  - TaskViewModel handles CRUD and Filter intents using Coroutines/Flow
  - ViewState correctly reflects the data from Repository

### Task_3_Adaptive_UI_Implementation: Develop the responsive Task Dashboard using Jetpack Compose Material 3 adaptive components. Implement the Task list, Priority filtering UI, and Task creation form/dialog. Apply a vibrant Material 3 color scheme and enable full edge-to-edge support.
- **Status:** COMPLETED
- **Updates:** Developed a responsive Task Dashboard with adaptive layouts for Compact and Expanded screen sizes. Implemented a vibrant Teal/Amber Material 3 theme with Dark mode support. Integrated Task list, Priority filtering, and Task creation (Dialog for mobile, Side panel for tablet). Enabled full edge-to-edge support. Connected all UI actions to the MVI ViewModel. Also implemented the Adaptive App Icon.
- **Acceptance Criteria:**
  - UI is responsive across different screen sizes (Compact, Medium, Expanded)
  - Vibrant Material 3 theme (Light/Dark) implemented
  - Add, Delete, and Filter actions integrated with ViewModel
  - Full edge-to-edge display enabled

### Task_4_Navigation_Icon_Verification: Integrate Jetpack Navigation 3 for app flow. Create an adaptive app icon matching the WorkspaceBuddy theme. Perform a final Run and Verify to ensure stability and requirement alignment.
- **Status:** COMPLETED
- **Updates:** Integrated Jetpack Navigation 3 for app flow. Verified adaptive app icon and edge-to-edge support. Critic agent confirmed application stability, vibrant Material 3 UI, and core functionality (CRUD, filtering). App is stable and ready.
- **Acceptance Criteria:**
  - Navigation 3 routing implemented
  - Adaptive app icon added
  - Project builds and runs without crashes
  - All existing tests pass
  - Critic_agent verifies application stability and UI alignment with requirements

### Task_5_DueDate_Logic_Integration: Update the Task domain model, Room entity, and MVI TaskContract to ensure full 'Due Date' support. Update the TaskViewModel to handle due date selection intents and state propagation.
- **Status:** COMPLETED
- **Updates:** Updated Task domain model, Room entity, and MVI TaskContract to support 'dueDateIso'. TaskViewModel now processes due date in the AddTask intent. Integrated Material 3 DatePicker into AddTaskDialog and SideAddTaskPanel. Updated TaskItem to display formatted due dates. Added API desugaring for java.time support. Verified with a successful build.
- **Acceptance Criteria:**
  - Domain and Data models support due date (ISO string)
  - MVI state and intents updated to include due date
  - TaskViewModel correctly processes due date data

### Task_6_DueDate_UI_and_Verification: Implement Material 3 DatePicker in the task creation dialog (mobile) and side panel (tablet). Display the due date in the task list items. Perform a final Run and Verify.
- **Status:** COMPLETED
- **Updates:** Verified that Material 3 DatePicker is integrated into both mobile and tablet task creation flows. Due dates are displayed and formatted correctly in the task list. App remains stable with full edge-to-edge support and vibrant theme. API desugaring confirmed.
- **Acceptance Criteria:**
  - DatePicker integrated into all task creation forms
  - Due date displayed and nicely formatted in the task list
  - Project builds and runs without crashes
  - Critic_agent verifies UI alignment and stability
- **Duration:** N/A

