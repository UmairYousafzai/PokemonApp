package com.test.pokemonapp.data.remote.models

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val menuID: String,
    val title: String,
    val icon: ImageVector,
    val contentDescription: String
)