package net.crystalapps.permission.runtime.library.callbacks

import net.crystalapps.permission.runtime.library.models.Perm

/**
 * Created by Syed Owais Ali on 5/8/2018.
 */

interface PermissionCallback<T> {
    fun onGranted(perm: Perm<T>)
    fun onDenied(perm: Perm<T>)
    fun onPermanentDenied(perm: Perm<T>)
}
