package com.test.pokemonapp.navDrawer

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.test.pokemonapp.R


@Composable
fun AppBar(
    onNavigationClick: () -> Unit
) {

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {

                Icon(imageVector = Icons.Default.Menu, contentDescription = "toggle drawer")
            }
        }
    )

}