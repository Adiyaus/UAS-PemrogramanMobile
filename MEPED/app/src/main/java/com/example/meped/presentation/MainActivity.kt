package com.example.meped.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.meped.MepedApplication
import com.example.meped.di.AppContainer
import com.example.meped.presentation.detail.DetailScreen
import com.example.meped.presentation.detail.DetailViewModel
import com.example.meped.presentation.explore.Explore
import com.example.meped.presentation.home.Home
import com.example.meped.presentation.login.LoginScreen
import com.example.meped.presentation.navigation.BottomNavigationBar
import com.example.meped.presentation.navigation.BottomNavItem
import com.example.meped.presentation.navigation.Screen
import com.example.meped.presentation.profile.Profil
import com.example.meped.presentation.schedule.Schedule
import com.example.meped.presentation.signup.SignUpScreen
import com.example.meped.presentation.survey.SurveyPlayerScreen
import com.example.meped.presentation.survey.SurveyTeamScreen
import com.example.meped.presentation.survey.SurveyTournamentScreen
import com.example.meped.presentation.ui.MEPEDTheme
import android.content.Intent
import android.net.Uri

class MainActivity : ComponentActivity() {
    private val appContainer by lazy { (application as MepedApplication).appContainer }
    private val sharedViewModel: SharedViewModel by viewModels {
        SharedViewModel.provideFactory(appContainer.teamRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MEPEDTheme {
                AppNav(
                    appContainer = appContainer,
                    sharedViewModel = sharedViewModel
                )
            }
        }
        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data
    }
}

@Composable
fun AppNav(appContainer: AppContainer, sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
    val repoViewModelFactory = MepedViewModelFactory(appContainer.teamRepository, onboardingManager = appContainer.onboardingManager)

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val mainScreenRoutes = listOf(BottomNavItem.Home.route, BottomNavItem.Schedule.route, BottomNavItem.Explore.route, BottomNavItem.Profile.route)
            if (currentDestination?.hierarchy?.any { it.route in mainScreenRoutes } == true) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- Alur Pra-Login ---
            composable(Screen.Login.route) {
                LoginScreen(
                    onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                    onSignInSuccess = { navController.navigate(Screen.Survey.route) { popUpTo(Screen.Login.route) { inclusive = true } } }
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignInClick = { navController.popBackStack() },
                    onSignUpSuccess = { navController.navigate(Screen.Survey.route) { popUpTo(Screen.Login.route) { inclusive = true } } }
                )
            }

            // PERBAIKAN 2: Berikan 'repoViewModelFactory' ke semua halaman survey
            composable(Screen.Survey.route) {
                SurveyTeamScreen(factory = repoViewModelFactory, sharedViewModel = sharedViewModel, onNextClick = { navController.navigate(Screen.PlayerUser.route) }, onSkipClick = { navController.navigate(Screen.PlayerUser.route) })
            }
            composable(Screen.PlayerUser.route) {
                SurveyPlayerScreen(factory = repoViewModelFactory, sharedViewModel = sharedViewModel, onNextClick = { navController.navigate(Screen.TournamentSurvey.route) }, onSkipClick = { navController.navigate(Screen.TournamentSurvey.route) })
            }
            composable(Screen.TournamentSurvey.route) {
                SurveyTournamentScreen(factory = repoViewModelFactory, sharedViewModel = sharedViewModel, onNextClick = { navController.navigate(BottomNavItem.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } } }, onSkipClick = { navController.navigate(BottomNavItem.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } } })
            }

            // --- Alur Halaman Utama ---
            composable(BottomNavItem.Home.route) {
                Home(navController = navController, factory = repoViewModelFactory)
            }
            composable(BottomNavItem.Schedule.route) {
                Schedule(factory = repoViewModelFactory)
            }
            composable(BottomNavItem.Explore.route) {
                Explore(factory = repoViewModelFactory, sharedViewModel = sharedViewModel, onNavigateToDetail = { navController.navigate(Screen.Detail.route) })
            }
            composable(BottomNavItem.Profile.route) {
                Profil(navController = navController, factory = repoViewModelFactory)
            }

            // --- Rute Halaman Detail ---
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("teamId") { type = NavType.StringType }),
                deepLinks = listOf(
                    navDeepLink { uriPattern = "meped://app/detail/{teamId}" }
                )
            ) { backStackEntry ->
                val teamId = backStackEntry.arguments?.getString("teamId") ?: "0"
                val detailViewModel: DetailViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return DetailViewModel(appContainer.teamRepository, sharedViewModel) as T
                        }
                    }
                )
                DetailScreen(viewModel = detailViewModel, navController = navController, teamId = teamId) // ARGUMEN DITAMBAHKAN
            }
        }
    }
}