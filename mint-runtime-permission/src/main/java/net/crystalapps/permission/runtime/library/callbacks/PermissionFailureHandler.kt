package net.crystalapps.permission.runtime.library.callbacks

import androidx.fragment.app.FragmentActivity

import net.crystalapps.permission.runtime.library.annotations.Permit

import java.lang.reflect.Method

interface PermissionFailureHandler {
    fun onFailed(failedPermissionList: List<Permit>, caller: FragmentActivity, method: Method)
}