package com.vpdevs.workspacebuddy.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Dashboard : Route
}
