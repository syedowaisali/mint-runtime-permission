package net.crystalapps.permission.runtime.library.models

/**
 * Created by Syed Owais Ali on 5/8/2018.
 */

interface Perm<T> {

    val type: String
    val requestCode: Int
    val caller: T
}
