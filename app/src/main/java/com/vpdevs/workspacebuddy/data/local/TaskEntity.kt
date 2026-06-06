package com.vpdevs.workspacebuddy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vpdevs.workspacebuddy.domain.model.Priority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean,
    val dueDateIso: String?
)
