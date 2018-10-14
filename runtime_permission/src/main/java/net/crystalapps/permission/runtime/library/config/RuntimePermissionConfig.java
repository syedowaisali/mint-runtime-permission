package net.crystalapps.permission.runtime.library.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler;
import net.crystalapps.permission.runtime.library.callbacks.PermissionFailureHandler;

@SuppressWarnings("unused")
public class RuntimePermissionConfig{

    private static RuntimePermissionConfig INSTANCE;

    @Nullable
    private PermanentDeniedHandler permanentDeniedHandler;

    @NonNull
    private final PermanentDeniedHandler defaultPermanentDeniedHandler;

    private PermissionFailureHandler permissionFailureHandler;

    private RuntimePermissionConfig() {
        defaultPermanentDeniedHandler = new MintPermanentDeniedHandler();
    }

    public static RuntimePermissionConfig getInstance() {
        return INSTANCE == null ? INSTANCE = new RuntimePermissionConfig() : INSTANCE;
    }

    public RuntimePermissionConfig setPermanentDeniedHandler(@Nullable PermanentDeniedHandler permanentDeniedHandler) {
        this.permanentDeniedHandler = permanentDeniedHandler;
        return this;
    }

    @NonNull
    public PermanentDeniedHandler getPermanentDeniedHandler() {
        return permanentDeniedHandler == null ? defaultPermanentDeniedHandler : permanentDeniedHandler;
    }

    public PermissionFailureHandler getPermissionFailureHandler() {
        return permissionFailureHandler;
    }

    public RuntimePermissionConfig setPermissionFailureHandler(@NonNull PermissionFailureHandler permissionFailureHandler) {
        this.permissionFailureHandler = permissionFailureHandler;
        return this;
    }
}