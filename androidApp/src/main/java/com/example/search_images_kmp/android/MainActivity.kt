package com.example.search_images_kmp.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.search_images_kmp.android.constant.AppScreen
import com.example.search_images_kmp.android.ui.bookmark.BookmarkScreen
import com.example.search_images_kmp.android.ui.search.SearchScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController()
) {
    val screens = mapOf(
        AppScreen.Search to Icons.Default.Search,
        AppScreen.Bookmark to Icons.Default.Favorite
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            BottomAppBar {
                screens.forEach { screen ->
                    val route = screen.key.name

                    NavigationBarItem(
                        selected = (currentDestination?.hierarchy?.any {
                            it.route == route
                        } == true),
                        onClick = {
                            navController.navigate(route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = screen.value,
                                contentDescription = route
                            )
                        },
                        label = {
                            Text(text = route)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            startDestination = AppScreen.Search.name,
            navController = navController
        ) {
            composable(AppScreen.Search.name) {
                SearchScreen()
            }
            composable(AppScreen.Bookmark.name) {
                BookmarkScreen()
            }
        }
    }
}
