package net.crystalapps.permission.runtime.library.core

import net.crystalapps.permission.runtime.library.annotations.Permit
import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler
import net.crystalapps.permission.runtime.library.callbacks.PermissionFailureHandler
import net.crystalapps.permission.runtime.library.callbacks.Request

import java.lang.reflect.Method
import java.util.Queue

/**
 * Created by Syed Owais Ali on 10/14/2018.
 */
internal class ServiceMethod(val permitQueue: Queue<Permit>, private val failurePermissionList: MutableList<Permit>, val callback: () -> Unit, val permanentDeniedHandler: PermanentDeniedHandler, val permissionFailureHandler: PermissionFailureHandler?, val method: Method) {

    fun getFailurePermissionList(): List<Permit> {
        return failurePermissionList
    }

    fun addToFailureList(permit: Permit) {
        failurePermissionList.add(permit)
    }

    fun removeToFailureList(permit: Permit) {
        failurePermissionList.remove(permit)
    }
}
