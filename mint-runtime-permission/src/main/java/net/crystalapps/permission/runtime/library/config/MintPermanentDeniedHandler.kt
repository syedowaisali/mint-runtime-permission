package net.crystalapps.permission.runtime.library.config

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity

import net.crystalapps.permission.runtime.library.annotations.Permit
import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler
import net.crystalapps.permission.runtime.library.callbacks.SettingOpener
import net.crystalapps.permission.runtime.library.utils.StringUtil

class MintPermanentDeniedHandler : PermanentDeniedHandler {

    override fun onHandle(permit: Permit, caller: FragmentActivity, settingOpener: SettingOpener<FragmentActivity>) {
        val builder = AlertDialog.Builder(caller)
        builder.setMessage(StringUtil.parseMessage(caller, permit.whenPermanentDenied, "Require " + permit.value + " permission to further process"))
        builder.setPositiveButton("OPEN SETTINGS") { dialogInterface, i -> settingOpener.open(caller, permit.value) }
        builder.setNegativeButton("NO") { dialogInterface, i -> settingOpener.doNothing(caller, permit.value) }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}