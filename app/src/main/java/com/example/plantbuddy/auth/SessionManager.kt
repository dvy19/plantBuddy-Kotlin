package com.example.plantbuddy.auth


import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ACCESS = "access_token"
        private const val KEY_REFRESH = "refresh_token"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }




    // ✅ Logout (clear session)
    fun logout() { prefs.edit().clear().apply() }


    fun saveTokens(access: String, refresh: String) {
        prefs.edit()
            .putString(KEY_ACCESS, access)
            .putString(KEY_REFRESH, refresh)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS, null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH, null)
    }

    fun isLoggedIn(): Boolean {
        return getAccessToken() != null &&
                getRefreshToken() != null
    }
}