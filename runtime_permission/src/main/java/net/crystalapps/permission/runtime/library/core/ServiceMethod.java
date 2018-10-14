package net.crystalapps.permission.runtime.library.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.crystalapps.permission.runtime.library.annotations.Permit;
import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler;
import net.crystalapps.permission.runtime.library.callbacks.PermissionFailureHandler;
import net.crystalapps.permission.runtime.library.callbacks.Request;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Queue;

/**
 * Created by Syed Owais Ali on 10/14/2018.
 */
class ServiceMethod {

    @NonNull
    private final Queue<Permit> permitQueue;

    @NonNull
    private final List<Permit> failurePermissionList;

    @NonNull
    private final Request request;

    @NonNull
    private final PermanentDeniedHandler permanentDeniedHandler;

    @Nullable
    private final PermissionFailureHandler permissionFailureHandler;

    @NonNull
    private final Method method;

    ServiceMethod(@NonNull Queue<Permit> permitQueue, @NonNull List<Permit> failurePermissionList, @NonNull Request request, @NonNull PermanentDeniedHandler permanentDeniedHandler, @Nullable PermissionFailureHandler permissionFailureHandler, @NonNull Method method) {
        this.permitQueue = permitQueue;
        this.failurePermissionList = failurePermissionList;
        this.request = request;
        this.permanentDeniedHandler = permanentDeniedHandler;
        this.permissionFailureHandler = permissionFailureHandler;
        this.method = method;
    }

    @NonNull
    Queue<Permit> getPermitQueue() {
        return permitQueue;
    }

    @NonNull
    List<Permit> getFailurePermissionList() {
        return failurePermissionList;
    }

    @NonNull
    Request getRequest() {
        return request;
    }

    @NonNull
    PermanentDeniedHandler getPermanentDeniedHandler() {
        return permanentDeniedHandler;
    }

    @Nullable
    PermissionFailureHandler getPermissionFailureHandler() {
        return permissionFailureHandler;
    }

    @NonNull
    public Method getMethod() {
        return method;
    }

    void addToFailureList(@NonNull Permit permit) {
        failurePermissionList.add(permit);
    }

    void removeToFailureList(@NonNull Permit permit) {
        failurePermissionList.remove(permit);
    }
}
