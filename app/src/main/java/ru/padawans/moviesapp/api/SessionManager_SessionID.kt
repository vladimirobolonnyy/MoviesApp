package ru.padawans.moviesapp.api

import android.content.Context
import android.content.SharedPreferences
import ru.padawans.moviesapp.R

class SessionManager_SessionID (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        var SESSION_ID = "session_id"
    }

    /**
     * Function to save auth SESSION_ID
     */
    fun saveAuthSessionId(session_id: String) {
        val editor = prefs.edit()
        editor.putString(SESSION_ID, session_id)
        editor.apply()
    }

    /**
     * Function to fetch auth SESSION_ID
     */
    fun fetchSessionID(): String? {
        return prefs.getString(SESSION_ID, null)
    }
}