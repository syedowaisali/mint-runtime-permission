package net.crystalapps.permission.runtime.library.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Syed Owais Ali on 10/14/2018.
 */
object StringUtil {

    fun parseMessage(context: Context, @StringRes resId: Int, defaultValue: String?): String? {
        return if (resId != -1) {
            try {
                context.resources.getString(resId)
            } catch (ex: Exception) {
                defaultValue
            }

        } else {
            defaultValue
        }
    }
}
