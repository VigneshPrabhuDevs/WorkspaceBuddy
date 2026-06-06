package com.vpdevs.workspacebuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.vpdevs.workspacebuddy.presentation.navigation.Route
import com.vpdevs.workspacebuddy.presentation.task.TaskDashboardScreen
import com.vpdevs.workspacebuddy.presentation.task.TaskViewModel
import com.vpdevs.workspacebuddy.ui.theme.WorkspaceBuddyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as SBApplication

        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(
                    app.getTasksUseCase,
                    app.addTaskUseCase,
                    app.deleteTaskUseCase,
                    app.toggleTaskCompletionUseCase
                ) as T
            }
        }

        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            WorkspaceBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val backStack = remember { mutableStateListOf<Any>(Route.Dashboard) }
                    
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = { key ->
                            when (key) {
                                is Route.Dashboard -> NavEntry(key) {
                                    TaskDashboardScreen(viewModel = viewModel)
                                }
                                else -> NavEntry(Unit) {
                                    Text("Unknown route")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
