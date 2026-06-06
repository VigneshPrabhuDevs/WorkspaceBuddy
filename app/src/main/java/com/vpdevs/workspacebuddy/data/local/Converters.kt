package com.vpdevs.workspacebuddy.data.local

import androidx.room.TypeConverter
import com.vpdevs.workspacebuddy.domain.model.Priority

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priorityName: String): Priority {
        return Priority.valueOf(priorityName)
    }
}
