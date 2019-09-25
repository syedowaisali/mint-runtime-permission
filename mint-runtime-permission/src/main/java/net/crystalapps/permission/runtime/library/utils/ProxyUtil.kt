package net.crystalapps.permission.runtime.library.utils

import androidx.fragment.app.FragmentActivity

import net.crystalapps.permission.runtime.library.core.DynamicPermProxyInvocationHandler
import net.crystalapps.permission.runtime.library.models.Perm
import net.crystalapps.permission.runtime.library.core.PermProxyInvocationHandler

import java.lang.reflect.Proxy

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

object ProxyUtil {

    fun <T> getProxy(permType: String, requestCode: Int, caller: T): Perm<T> {
        return Proxy.newProxyInstance(Perm::class.java.classLoader, arrayOf<Class<*>>(Perm::class.java), PermProxyInvocationHandler(permType, requestCode, caller)) as Perm<T>
    }

    fun <T : FragmentActivity, S> getDynamicProxy(act: T, service: Class<S>): S {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service), DynamicPermProxyInvocationHandler(act, service)) as S
    }
}
