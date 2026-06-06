package com.vpdevs.workspacebuddy.domain.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val dueDateIso: String? = null
)
