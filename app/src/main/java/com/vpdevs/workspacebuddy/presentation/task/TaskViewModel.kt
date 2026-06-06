package com.vpdevs.workspacebuddy.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.usecase.AddTaskUseCase
import com.vpdevs.workspacebuddy.domain.usecase.DeleteTaskUseCase
import com.vpdevs.workspacebuddy.domain.usecase.GetTasksUseCase
import com.vpdevs.workspacebuddy.domain.usecase.ToggleTaskCompletionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(TaskViewState())
    val viewState: StateFlow<TaskViewState> = _viewState.asStateFlow()

    private val _sideEffect = Channel<TaskSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        handleIntent(TaskUiIntent.LoadTasks)
    }

    fun handleIntent(intent: TaskUiIntent) {
        when (intent) {
            is TaskUiIntent.LoadTasks -> loadTasks()
            is TaskUiIntent.AddTask -> addTask(intent)
            is TaskUiIntent.DeleteTask -> deleteTask(intent.task)
            is TaskUiIntent.ToggleTask -> toggleTask(intent.task)
            is TaskUiIntent.ChangeFilter -> changeFilter(intent)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            getTasksUseCase(_viewState.value.filter)
                .catch { error ->
                    _viewState.update { it.copy(isLoading = false, error = error.message) }
                    _sideEffect.send(TaskSideEffect.ShowError(error.message ?: "Unknown error"))
                }
                .collectLatest { tasks ->
                    _viewState.update { it.copy(tasks = tasks, isLoading = false, error = null) }
                }
        }
    }

    private fun addTask(intent: TaskUiIntent.AddTask) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newTask = Task(
                    title = intent.title,
                    description = intent.description,
                    priority = intent.priority,
                    dueDateIso = intent.dueDateIso
                )
                addTaskUseCase(newTask)
            } catch (e: Exception) {
                _sideEffect.send(TaskSideEffect.ShowError(e.message ?: "Failed to add task"))
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(task)
            } catch (e: Exception) {
                _sideEffect.send(TaskSideEffect.ShowError(e.message ?: "Failed to delete task"))
            }
        }
    }

    private fun toggleTask(task: Task) {
        viewModelScope.launch {
            try {
                toggleTaskCompletionUseCase(task)
            } catch (e: Exception) {
                _sideEffect.send(TaskSideEffect.ShowError(e.message ?: "Failed to toggle task"))
            }
        }
    }

    private fun changeFilter(intent: TaskUiIntent.ChangeFilter) {
        _viewState.update { it.copy(filter = intent.priority) }
        loadTasks()
    }
}
