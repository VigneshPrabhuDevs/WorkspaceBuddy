package com.vpdevs.workspacebuddy.presentation.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.presentation.task.components.AddTaskDialog
import com.vpdevs.workspacebuddy.presentation.task.components.PriorityFilter
import com.vpdevs.workspacebuddy.presentation.task.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDashboardScreen(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.viewState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isExpanded = adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "WorkspaceBuddy",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (!isExpanded) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Main Content: Task List and Filter
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                PriorityFilter(
                    selectedPriority = state.filter,
                    onPrioritySelected = { viewModel.handleIntent(TaskUiIntent.ChangeFilter(it)) },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (state.tasks.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "No tasks found. Add some!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.tasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onToggle = { viewModel.handleIntent(TaskUiIntent.ToggleTask(task)) },
                                onDelete = { viewModel.handleIntent(TaskUiIntent.DeleteTask(task)) }
                            )
                        }
                    }
                }
            }

            // Side Panel for Expanded Screens
            if (isExpanded) {
                SideAddTaskPanel(
                    onAddTask = { title, description, priority, dueDateIso ->
                        viewModel.handleIntent(TaskUiIntent.AddTask(title, description, priority, dueDateIso))
                    },
                    modifier = Modifier
                        .width(350.dp)
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
        }
    }

    if (showAddDialog && !isExpanded) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, description, priority, dueDateIso ->
                viewModel.handleIntent(TaskUiIntent.AddTask(title, description, priority, dueDateIso))
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideAddTaskPanel(
    onAddTask: (String, String, Priority, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var dueDateIso by remember { mutableStateOf<String?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        dueDateIso = date.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        Text("Add New Task", style = MaterialTheme.typography.headlineSmall)
        androidx.compose.material3.OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        androidx.compose.material3.OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(text = dueDateIso?.let { "Due: $it" } ?: "No due date")
            androidx.compose.material3.IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Rounded.DateRange, contentDescription = "Select Date")
            }
        }

        Text("Priority")
        Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
            Priority.entries.forEach { p ->
                androidx.compose.material3.FilterChip(
                    selected = priority == p,
                    onClick = { priority = p },
                    label = { Text(p.name) }
                )
            }
        }
        androidx.compose.material3.Button(
            onClick = {
                onAddTask(title, description, priority, dueDateIso)
                title = ""
                description = ""
                dueDateIso = null
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}
