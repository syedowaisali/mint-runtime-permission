package net.crystalapps.permission.runtime.library.config

import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler
import net.crystalapps.permission.runtime.library.callbacks.PermissionFailureHandler

class RuntimePermissionConfig private constructor() {

    private var permanentDeniedHandler: PermanentDeniedHandler? = null

    private val defaultPermanentDeniedHandler: PermanentDeniedHandler = MintPermanentDeniedHandler()

    private var permissionFailureHandler: PermissionFailureHandler? = null



    fun setPermanentDeniedHandler(permanentDeniedHandler: PermanentDeniedHandler?): RuntimePermissionConfig {
        this.permanentDeniedHandler = permanentDeniedHandler
        return this
    }

    fun getPermanentDeniedHandler(): PermanentDeniedHandler {
        return permanentDeniedHandler ?: defaultPermanentDeniedHandler
    }

    fun getPermissionFailureHandler(): PermissionFailureHandler? {
        return permissionFailureHandler
    }

    fun setPermissionFailureHandler(permissionFailureHandler: PermissionFailureHandler): RuntimePermissionConfig {
        this.permissionFailureHandler = permissionFailureHandler
        return this
    }

    companion object {

        private var INSTANCE: RuntimePermissionConfig? = null

        val instance: RuntimePermissionConfig
            get() {
                if (INSTANCE == null) INSTANCE = RuntimePermissionConfig()
                return INSTANCE!!
            }
    }
}