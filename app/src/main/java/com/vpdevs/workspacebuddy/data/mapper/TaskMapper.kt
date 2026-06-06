package com.vpdevs.workspacebuddy.data.mapper

import com.vpdevs.workspacebuddy.data.local.TaskEntity
import com.vpdevs.workspacebuddy.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        isCompleted = isCompleted,
        dueDateIso = dueDateIso
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        isCompleted = isCompleted,
        dueDateIso = dueDateIso
    )
}
