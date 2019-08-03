package com.template.app.utils

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManager @Inject constructor(private var preferences: SharedPreferences) {

    fun setString(key: String, value: String) {
        preferences.edit()
                .putString(key, value)
                .apply()
    }

    fun getString(key: String, defValue: String): String? {
        return preferences.getString(key, defValue)
    }


    fun setInt(key: String, value: Int) {
        preferences.edit()
                .putInt(key, value)
                .apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun setLong(key: String, value: Long) {
        preferences.edit()
                .putLong(key, value)
                .apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    fun setFloat(key: String, value: Float) {
         preferences.edit()
                 .putFloat(key, value)
                 .apply()
    }

    fun getFloat(key: String, defValue: Int): Float {
        return preferences.getFloat(key, defValue.toFloat())
    }


    fun setBoolean(key: String, value: Boolean) {
        preferences.edit()
                .putBoolean(key, value)
                .apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    fun clearKey(key: String) {
         preferences.edit()
                .remove(key)
                .apply()
    }
}