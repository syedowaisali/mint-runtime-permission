package net.crystalapps.permission.runtime.library.callbacks


import androidx.fragment.app.FragmentActivity

interface SettingOpener<T : FragmentActivity> {
    fun open(caller: T, permission: String)
    fun doNothing(caller: T, permission: String)
}