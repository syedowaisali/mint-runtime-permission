package net.crystalapps.permission.runtime.library.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
class PermProxyInvocationHandler<T>(private val type: String, private val requestCode: Int, private val caller: T) : InvocationHandler {

    override fun invoke(o: Any?, method: Method?, objects: Array<out Any>?): Any {

        return when (method?.name) {
            "getType" -> type
            "getRequestCode" -> requestCode
            "getCaller" -> caller as Any

            else -> throw UnsupportedOperationException("Unsupported method: " + method?.name)
        }
    }
}
