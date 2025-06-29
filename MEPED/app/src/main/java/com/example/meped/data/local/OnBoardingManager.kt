package com.example.meped.data.local

import android.content.Context
import android.content.SharedPreferences

class OnboardingManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)

    fun hasSeenHomeOnboarding(): Boolean {
        return prefs.getBoolean(KEY_HOME_ONBOARDING, false)
    }

    fun setHomeOnboardingSeen() {
        prefs.edit().putBoolean(KEY_HOME_ONBOARDING, true).apply()
    }

    companion object {
        private const val KEY_HOME_ONBOARDING = "has_seen_home_onboarding"
    }
}