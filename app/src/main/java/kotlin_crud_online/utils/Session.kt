package kotlin_crud_online.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import kotlin_crud_online.models.MenuActivity
import kotlin_crud_online.models.SigninActivity
import kotlin_crud_online.models.SignupActivity
import java.util.HashMap

class Session(internal var _context: Context) {
    // Shared Preferences
    internal var pref: SharedPreferences

    // Editor for Shared preferences
    internal var editor: SharedPreferences.Editor

    val KEY_USERNAME : String = "username"

    // Shared pref mode
    internal var PRIVATE_MODE = 0

    fun getUserDetails(): HashMap<String, String> {
        val user = HashMap<String, String>()

        // user email id
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null))
        // return user
        return user
    }

    /**
     * Quick check for login
     */
    // Get Login State
    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    /**
     *
     * Create login session
     */
    fun createLoginSession(username: String) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)

        // Storing email in pref
        editor.putString(KEY_USERNAME, username)

        // commit changes
        editor.commit()
    }

    /**
     * Check login method wil check user login status
     * If true it will redirect user to UserActivity Page
     * Else won't do anything
     */
    fun checkLogin() {
        // Check login status
        if (this.isLoggedIn) {
            // user is not logged in redirect him to Login Activity
            val i = Intent(_context, MenuActivity::class.java)
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

            // Staring Main Activity
            _context.startActivity(i)
        }

    }

    /**
     * Clear session details
     */
    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()

        // After logout redirect user to Loing Activity
        val intent = Intent(_context, SigninActivity::class.java)
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        // Staring Login Activity
        _context.startActivity(intent)
    }

    companion object {

        // Sharedpref file name
        private val PREF_NAME = "AkbarDeBruyne"

        // All Shared Preferences Keys
        private val IS_LOGIN = "IsLoggedIn"

        // Email address (make variable public to access from outside)

    }
}
