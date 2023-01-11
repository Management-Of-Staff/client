package myapplication.second.workinghourmanagement

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("local_db", Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return prefs.getString(key, "").toString()
//        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}