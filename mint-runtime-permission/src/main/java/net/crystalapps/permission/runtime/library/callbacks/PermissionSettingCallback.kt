package net.crystalapps.permission.runtime.library.callbacks

import net.crystalapps.permission.runtime.library.models.Perm

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
interface PermissionSettingCallback<T> {
    fun onGranted(perm: Perm<T>)
    fun onDenied(perm: Perm<T>)
}
