package com.example.iouapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.iouapp.data.User
import com.example.iouapp.ui.AddUserScreen
import com.example.iouapp.ui.EditUserScreen
import com.example.iouapp.ui.UserListScreen
import com.example.iouapp.ui.theme.IOUAppTheme
import com.example.iouapp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IOUAppTheme {
                IOUNavHost(viewModel = userViewModel)
            }
        }
    }
}

private object Destinations {
    const val USER_LIST = "user_list"
    const val ADD_USER = "add_user"
    const val EDIT_USER = "edit_user"
    const val USER_ID_ARG = "userId"
}

@Composable
fun IOUNavHost(viewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.USER_LIST
    ) {
        composable(Destinations.USER_LIST) {
            val users by viewModel.users.collectAsStateWithLifecycle()
            UserListScreen(
                users = users,
                onAddClick = { navController.navigate(Destinations.ADD_USER) },
                onUserClick = { user ->
                    navController.navigate("${Destinations.EDIT_USER}/${user.id}")
                }
            )
        }
        composable(Destinations.ADD_USER) {
            AddUserScreen(
                onNavigateBack = { navController.popBackStack() },
                onSave = { name ->
                    val trimmed = name.trim()
                    if (trimmed.isEmpty()) {
                        false
                    } else {
                        viewModel.addUser(trimmed)
                        true
                    }
                }
            )
        }
        composable(
            route = "${Destinations.EDIT_USER}/{${Destinations.USER_ID_ARG}}",
            arguments = listOf(navArgument(Destinations.USER_ID_ARG) {
                type = NavType.LongType
            })
        ) { entry ->
            val userId = entry.arguments?.getLong(Destinations.USER_ID_ARG) ?: return@composable
            val userState by viewModel.user(userId).collectAsStateWithLifecycle(initialValue = null)
            EditUserScreen(
                user = userState,
                onNavigateBack = { navController.popBackStack() },
                onSave = { user: User, newName: String ->
                    val trimmed = newName.trim()
                    if (trimmed.isEmpty()) {
                        false
                    } else {
                        viewModel.updateUser(user, trimmed)
                        true
                    }
                }
            )
        }
    }
}
