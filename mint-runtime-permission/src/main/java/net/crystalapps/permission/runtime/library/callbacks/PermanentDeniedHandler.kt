package net.crystalapps.permission.runtime.library.callbacks


import androidx.fragment.app.FragmentActivity

import net.crystalapps.permission.runtime.library.annotations.Permit

interface PermanentDeniedHandler {
    fun onHandle(permit: Permit, caller: FragmentActivity, settingOpener: SettingOpener<FragmentActivity>)
}