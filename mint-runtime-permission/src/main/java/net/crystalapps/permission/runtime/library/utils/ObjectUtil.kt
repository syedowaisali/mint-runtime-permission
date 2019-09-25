package net.crystalapps.permission.runtime.library.utils

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
object ObjectUtil {

    fun <T> requireNonNull(obj: T): T {
        return requireNonNull(obj, null)
    }

    fun <T> requireNonNull(obj: T?, message: String?): T {
        if (obj == null)
            throw NullPointerException(message)
        return obj
    }
}
