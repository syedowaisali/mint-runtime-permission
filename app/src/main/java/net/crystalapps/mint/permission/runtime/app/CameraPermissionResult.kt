package net.crystalapps.mint.permission.runtime.app

import android.app.AlertDialog
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast

import net.crystalapps.permission.runtime.library.core.RuntimePermission
import net.crystalapps.permission.runtime.library.models.Perm
import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
class CameraPermissionResult : PermissionCallback<MainActivity> {

    override fun onGranted(perm: Perm<MainActivity>) {
        perm.caller.startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 19)
    }

    override fun onDenied(perm: Perm<MainActivity>) {
        Toast.makeText(perm.caller, "Permission denied", Toast.LENGTH_LONG).show()
    }

    override fun onPermanentDenied(perm: Perm<MainActivity>) {
        val builder = AlertDialog.Builder(perm.caller)
        builder.setMessage("Allow camera permission in app detail option.")
        builder.setPositiveButton("OK") { dialogInterface, i -> RuntimePermission.openAppSettingIntent(perm.caller, perm.type, SettingCallback()) }
        builder.create().show()
    }

    private inner class SettingCallback : PermissionSettingCallback<MainActivity> {

        override fun onGranted(perm: Perm<MainActivity>) {
            perm.caller.startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        override fun onDenied(perm: Perm<MainActivity>) {
            Toast.makeText(perm.caller, "Permission denied from settings", Toast.LENGTH_LONG).show()
        }
    }
}
