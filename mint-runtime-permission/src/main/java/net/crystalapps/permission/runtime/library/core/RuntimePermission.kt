package net.crystalapps.permission.runtime.library.core

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback
import net.crystalapps.permission.runtime.library.models.Perm
import net.crystalapps.permission.runtime.library.utils.ObjectUtil
import net.crystalapps.permission.runtime.library.utils.PersistentUtil
import net.crystalapps.permission.runtime.library.utils.ProxyUtil

/**
 * Created by Syed Owais Ali on 5/7/2018.
 */

object RuntimePermission {

    val isPermissionRequired: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun hasPermission(context: Context, perm: String): Boolean {
        return !isPermissionRequired || ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }

    fun <T : Fragment> requestPermission(caller: T, permType: String, callback: PermissionCallback<T>) {
        requestPermission(caller, permType, 1947, callback)
    }

    fun <T : Fragment> requestPermission(caller: T, permType: String, requestCode: Int, callback: PermissionCallback<T>) {

        requestPermission(caller.activity!!, caller, permType, 1947, callback)
    }

    fun <T : FragmentActivity> requestPermission(caller: T, permType: String, callback: PermissionCallback<T>) {
        requestPermission(caller, permType, 1947, callback)
    }

    fun <T : FragmentActivity> requestPermission(caller: T, permType: String, requestCode: Int, callback: PermissionCallback<T>) {
        requestPermission(caller, caller, permType, requestCode, callback)
    }

    private fun <T> requestPermission(activity: FragmentActivity, caller: T, permType: String, requestCode: Int, callback: PermissionCallback<T>) {

        // null check
        ObjectUtil.requireNonNull(caller, "caller cannot be null")
        ObjectUtil.requireNonNull(permType, "permission cannot be null")
        ObjectUtil.requireNonNull(callback, "callback cannot be null")

        val perm = ProxyUtil.getProxy(permType, requestCode, caller)

        if (isPermissionRequired && !hasPermission(activity, permType)) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, perm.type) && !PersistentUtil.isCameFirst(activity, perm.type)) {
                callback.onPermanentDenied(perm)
            } else {
                val fragmentManager = activity.supportFragmentManager
                val fragment = PermsFragment.getInstance(perm, callback)
                fragmentManager.beginTransaction().add(fragment, "PERMS_FRAGMENT").commitNow()
                //fragmentManager.executePendingTransactions();
                fragment.requestPermissions(arrayOf(perm.type), perm.requestCode)
            }
        } else {
            callback.onGranted(perm)
        }
    }

    fun <T : Fragment> openAppSettingIntent(caller: T, permType: String, callback: PermissionSettingCallback<T>) {
        openAppSettingIntent(caller, permType, 148, callback)
    }

    fun <T : Fragment> openAppSettingIntent(caller: T, permType: String, requestCode: Int, callback: PermissionSettingCallback<T>) {

        openAppSettingIntent(caller.activity!!, caller, permType, requestCode, callback)
    }

    fun <T : FragmentActivity> openAppSettingIntent(caller: T, permType: String, callback: PermissionSettingCallback<T>) {
        openAppSettingIntent(caller, permType, 148, callback)
    }

    fun <T : FragmentActivity> openAppSettingIntent(caller: T, permType: String, requestCode: Int, callback: PermissionSettingCallback<T>) {
        openAppSettingIntent(caller, caller, permType, requestCode, callback)
    }

    private fun <T> openAppSettingIntent(activity: FragmentActivity, caller: T, permType: String, requestCode: Int, callback: PermissionSettingCallback<T>) {

        // null check
        ObjectUtil.requireNonNull(caller, "caller cannot be null")
        ObjectUtil.requireNonNull(permType, "permission cannot be null")
        ObjectUtil.requireNonNull(callback, "callback cannot be null")

        val perm = ProxyUtil.getProxy(permType, requestCode, caller)

        if (isPermissionRequired) {

            val fragmentManager = activity.supportFragmentManager
            val fragment = AppSettingFragment.getInstance(perm, callback)
            fragmentManager.beginTransaction().add(fragment, "APP_SETTING_FRAGMENT").commitNow()
            //fragmentManager.executePendingTransactions();

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null))
            fragment.startActivityForResult(intent, perm.requestCode)
        } else {
            callback.onGranted(perm)
        }
    }

    fun <T : Fragment, S> ask(caller: T, service: Class<S>): S {

        return ask(caller.activity!!, service)
    }

    fun <T : FragmentActivity, S> ask(caller: T, service: Class<S>): S {
        return ProxyUtil.getDynamicProxy(caller, service)
    }
}
