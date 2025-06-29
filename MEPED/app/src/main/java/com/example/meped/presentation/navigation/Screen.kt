package com.example.meped.presentation.navigation

sealed class Screen(val route: String) {
    // Alur Pra-Login & Survey
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Survey : Screen("survey_team")
    object PlayerUser : Screen("survey_player")
    object TournamentSurvey : Screen("survey_tournament")

    object Detail : Screen("detail") {
        fun createRoute(teamId: String) = "detail/$teamId"
    }
}