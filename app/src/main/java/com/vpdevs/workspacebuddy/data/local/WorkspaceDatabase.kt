package com.vpdevs.workspacebuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorkspaceDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "workspace_db"
    }
}
