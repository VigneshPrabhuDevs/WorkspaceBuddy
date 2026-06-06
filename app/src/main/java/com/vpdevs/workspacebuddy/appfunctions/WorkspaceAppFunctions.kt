package com.vpdevs.workspacebuddy.appfunctions

import androidx.appfunctions.AppFunctionContext
import androidx.appfunctions.service.AppFunction
import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.usecase.AddTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class WorkspaceAppFunctions(
    private val addTaskUseCase: AddTaskUseCase
) {

    /**
     * Creates a new task or reminder for the user.
     *
     * @param context The execution context provided by the system.
     * @param title The description of what needs to be done.
     * @param description A more detailed explanation of the task.
     * @param priority The importance tier of the task. Allowed values: "HIGH", "MEDIUM", "LOW". Default is "MEDIUM".
     * @param dueDateIso The due date in ISO format (e.g., "2023-12-31").
     * @return A message confirming the task creation status.
     */
    @AppFunction(isDescribedByKDoc = true)
    suspend fun createTask(
        context: AppFunctionContext,
        title: String,
        description: String? = null,
        priority: String? = null,
        dueDateIso: String? = null
    ): String = withContext(Dispatchers.IO) {
        try {
            val priorityEnum = try {
                if (priority != null) {
                    Priority.valueOf(priority.uppercase(Locale.ROOT))
                } else {
                    Priority.MEDIUM
                }
            } catch (e: Exception) {
                Priority.MEDIUM
            }

            val newTask = Task(
                title = title,
                description = description ?: "",
                priority = priorityEnum,
                dueDateIso = dueDateIso
            )

            addTaskUseCase(newTask)
            "Successfully added task: '$title' with ${priorityEnum.name} priority."
        } catch (e: Exception) {
            "Failed to add task: ${e.localizedMessage}"
        }
    }
}
