package net.crystalapps.permission.runtime.library.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
object PersistentUtil {

    fun isCameFirst(context: Context, permType: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("mint_core_rp", Context.MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean(permType, true)
        val editor = sharedPreferences.edit()
        editor.putBoolean(permType, false)
        editor.apply()
        return isFirstTime
    }
}
