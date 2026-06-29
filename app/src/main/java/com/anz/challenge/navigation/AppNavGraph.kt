package com.anz.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anz.challenge.feature.users.presentation.UserDetailScreen
import com.anz.challenge.feature.users.presentation.UsersListScreen
import com.anz.challenge.feature.users.presentation.UsersViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: UsersViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "users_list"
    ) {
        composable("users_list") {
            UsersListScreen (
                viewModel = viewModel,
                onUserClick = { user ->
                    navController.navigate("user_detail/${user.id}")
                }
            )
        }

        composable("user_detail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            UserDetailScreen(
                viewModel = viewModel,
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}