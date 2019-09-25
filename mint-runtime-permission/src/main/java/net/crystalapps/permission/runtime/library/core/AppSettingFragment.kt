package net.crystalapps.permission.runtime.library.core

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import net.crystalapps.permission.runtime.library.models.Perm
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback
import net.crystalapps.permission.runtime.library.utils.ObjectUtil

import java.util.Objects

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
class AppSettingFragment<T> : Fragment() {

    private var perm: Perm<T>? = null
    private var callback: PermissionSettingCallback<T>? = null

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (RuntimePermission.hasPermission(context!!, perm!!.type)) {
            callback!!.onGranted(perm!!)
        } else {
            callback!!.onDenied(perm!!)
        }

        val fragmentManager = fragmentManager
        fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
    }

    companion object {

        fun <T> getInstance(perm: Perm<T>, callback: PermissionSettingCallback<T>): AppSettingFragment<*> {
            val fragment = AppSettingFragment<T>()
            fragment.perm = perm
            fragment.callback = callback
            return fragment
        }
    }
}