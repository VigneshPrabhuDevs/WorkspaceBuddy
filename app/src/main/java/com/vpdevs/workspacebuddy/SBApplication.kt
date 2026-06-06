package com.vpdevs.workspacebuddy

import android.app.Application
import androidx.appfunctions.service.AppFunctionConfiguration
import androidx.room.Room
import com.vpdevs.workspacebuddy.appfunctions.WorkspaceAppFunctions
import com.vpdevs.workspacebuddy.data.local.WorkspaceDatabase
import com.vpdevs.workspacebuddy.data.repository.TaskRepositoryImpl
import com.vpdevs.workspacebuddy.domain.usecase.AddTaskUseCase
import com.vpdevs.workspacebuddy.domain.usecase.DeleteTaskUseCase
import com.vpdevs.workspacebuddy.domain.usecase.GetTasksUseCase
import com.vpdevs.workspacebuddy.domain.usecase.ToggleTaskCompletionUseCase

class SBApplication : Application(), AppFunctionConfiguration.Provider {

    private val database by lazy {
        Room.databaseBuilder(
            this,
            WorkspaceDatabase::class.java,
            WorkspaceDatabase.DATABASE_NAME
        ).build()
    }

    private val taskRepository by lazy { TaskRepositoryImpl(database.taskDao) }

    val getTasksUseCase by lazy { GetTasksUseCase(taskRepository) }
    val addTaskUseCase by lazy { AddTaskUseCase(taskRepository) }
    val deleteTaskUseCase by lazy { DeleteTaskUseCase(taskRepository) }
    val toggleTaskCompletionUseCase by lazy { ToggleTaskCompletionUseCase(taskRepository) }

    private val workspaceAppFunctions by lazy { WorkspaceAppFunctions(addTaskUseCase) }

    override val appFunctionConfiguration: AppFunctionConfiguration
        get() = AppFunctionConfiguration.Builder()
            .addEnclosingClassFactory(WorkspaceAppFunctions::class.java) {
                workspaceAppFunctions
            }
            .build()
}
