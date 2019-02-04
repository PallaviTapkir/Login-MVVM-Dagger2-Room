/*
 * AppPreferences.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.config

import android.content.Context
import android.content.SharedPreferences

/**
 * Class for saving required values to locally using key value pair
 */

class AppPreferences
/**
 * Constructor
 *
 * @param context
 */
private constructor(context: Context) {

    private val appSharedPrefs: SharedPreferences = context.getSharedPreferences(
        Config.SHARED_PREFERENCES,
        Context.MODE_PRIVATE
    )
    private val prefsEditor: SharedPreferences.Editor

    // initialize shared preference
    init {
        this.prefsEditor = appSharedPrefs.edit()
    }

    /**
     * Save tank
     * @name Key for save tank
     * @isEnabled flag
     */
    fun saveUserId(userId: Long) {
        prefsEditor.putLong(PREF_KEY_USER_ID, userId)
        prefsEditor.commit()
    }

    /**
     * return whether tank is saved or not
     * @name Key for tank
     */
    fun getUserId(): Long {
        return appSharedPrefs.getLong(PREF_KEY_USER_ID, 0)
    }

    /**
     * All constant and static variable/methods declare in companion object
     */
    companion object {

        //Application Shared Preferences keys
        const val PREF_KEY_USER_ID = "pref_user_id"

        private var mInstance: AppPreferences? = null

        // returns instance of preference
        fun getInstance(context: Context): AppPreferences {
            if (mInstance == null) {
                mInstance = AppPreferences(context)
            }
            return mInstance as AppPreferences
        }
    }
}
