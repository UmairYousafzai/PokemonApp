package com.test.pokemonapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.pokemonapp.data.remote.models.MenuItem
import com.test.pokemonapp.navDrawer.AppBar
import com.test.pokemonapp.navDrawer.DrawerBody
import com.test.pokemonapp.navDrawer.DrawerHeader
import com.test.pokemonapp.pokemonDetail.PokemonDetailScreen
import com.test.pokemonapp.pokemonlist.PokemonListScreen
import com.test.pokemonapp.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    modifier = Modifier,
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(
                            onNavigationClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    }, drawerContent = {
                        DrawerHeader()
                        DrawerBody(menuItems = listOf(
                            MenuItem(
                                "home",
                                "Home",
                                Icons.Default.Home,
                                "home"
                            ), MenuItem(
                                "setting",
                                "Setting",
                                Icons.Default.Settings,
                                "setting"
                            ), MenuItem(
                                "help",
                                "Help",
                                Icons.Default.Star,
                                "help"
                            ), MenuItem(
                                "share",
                                "Share",
                                Icons.Default.Share,
                                "Share"
                            ), MenuItem(
                                "close",
                                "Close",
                                Icons.Default.Close,
                                "close"
                            )
                        ), onClickItem = {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(it.title)
                            }
                        })
                    }
                )
                {

                    NavHost(
                        navController = navController,
                        startDestination = "pokemon_list_screen",
                        modifier = Modifier.padding(it)
                    ) {
                        composable("pokemon_list_screen") {
                            PokemonListScreen(navController = navController)
                        }
                        composable("pokemon_detail_screen/{dominantColor}/{name}",
                            arguments = listOf(
                                navArgument("dominantColor")
                                {
                                    type = NavType.IntType
                                },
                                navArgument("name")
                                {
                                    type = NavType.StringType
                                }
                            )) {
                            val color = remember { it.arguments?.getInt("dominantColor") }
                            val dominantColor = color?.let { Color(it) } ?: Color.White

                            val name = remember { it.arguments?.getString("name") }

                            PokemonDetailScreen(
                                dominantColor = dominantColor,
                                pokemonName = name ?: "",
                                navController = navController
                            )
                        }
                    }

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonAppTheme {

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "pokemon_list_screen") {
            composable("pokemon_list_screen") {
                PokemonListScreen(navController = navController)
            }
            composable("pokemon_detail_screen/{dominantColor}/{name}", arguments = listOf(
                navArgument("dominantColor")
                {
                    type = NavType.IntType
                },
                navArgument("name")
                {
                    type = NavType.StringType
                }
            )) {
                val color = remember { it.arguments?.getInt("dominantColor") }
                color?.let { Color(it) } ?: Color.White

                val name = remember { it.arguments?.getString("name") }

            }
        }
    }
}