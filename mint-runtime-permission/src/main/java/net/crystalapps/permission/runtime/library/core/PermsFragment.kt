package net.crystalapps.permission.runtime.library.core

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback
import net.crystalapps.permission.runtime.library.models.Perm

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
class PermsFragment<T> : Fragment() {

    private var perm: Perm<T>? = null
    private var callback: PermissionCallback<T>? = null

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == perm!!.requestCode && grantResults.size > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback!!.onGranted(perm!!)
            } else {
                callback!!.onDenied(perm!!)
            }

            // remove fragment from manager
            val fragmentManager = fragmentManager
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    companion object {

        fun <T> getInstance(perm: Perm<T>, callback: PermissionCallback<T>): PermsFragment<*> {
            val fragment = PermsFragment<T>()
            fragment.perm = perm
            fragment.callback = callback
            return fragment
        }
    }
}