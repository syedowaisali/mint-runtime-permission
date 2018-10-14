package net.crystalapps.permission.runtime.library.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.annotations.PermanentDenied;
import net.crystalapps.permission.runtime.library.annotations.PermissionContainer;
import net.crystalapps.permission.runtime.library.annotations.PermissionFailure;
import net.crystalapps.permission.runtime.library.annotations.Permit;
import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler;
import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback;
import net.crystalapps.permission.runtime.library.callbacks.PermissionFailureHandler;
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback;
import net.crystalapps.permission.runtime.library.callbacks.Request;
import net.crystalapps.permission.runtime.library.callbacks.SettingOpener;
import net.crystalapps.permission.runtime.library.config.RuntimePermissionConfig;
import net.crystalapps.permission.runtime.library.models.Perm;
import net.crystalapps.permission.runtime.library.utils.ObjectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class DynamicPermProxyInvocationHandler<T extends FragmentActivity, S> implements InvocationHandler {

    private final T act;
    private final Class<S> service;

    public DynamicPermProxyInvocationHandler(T act, Class<S> service) {
        this.act = act;
        this.service = service;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) {

        if (! method.getReturnType().getTypeName().equals("void")) {
            throw new IllegalStateException("Return type of " + method.getName() + " method should be void");
        }

        if (objects == null) {
            throw new IllegalStateException(Request.class.getName() + " parameter not found in " + method.getName() + " method");
        }

        if (objects.length != 1) {
            throw new IllegalStateException(method.getName() + " method should be contain only 1 parameter");
        }

        if (objects[0] == null) {
            throw new NullPointerException("parameter should not be null");
        }

        if (!(objects[0] instanceof Request)) {
            throw new IllegalArgumentException("Invalid parameter found in " + method.getName() + " method, param type should be " + Request.class.getName());
        }

        if (!method.isAnnotationPresent(Permit.class) && !method.isAnnotationPresent(PermissionContainer.class)) {
            throw new IllegalStateException("Add at least one Permit or PermissionContainer annotation in " + method.getName() + " method");
        }

        List<Permit> failedPermissionsList = new ArrayList<>();
        ServiceMethod serviceMethod = new ServiceMethod(
                normalize(method),
                failedPermissionsList,
                (Request) objects[0],
                getPermanentDeniedHandler(method, service),
                getPermissionFailureHandler(method, service),
                method
        );

        process(serviceMethod);

        return null;
    }

    private Queue<Permit> normalize(Method method) {
        Queue<Permit> queue = new LinkedList<>();

        if (method.isAnnotationPresent(Permit.class)) {
            queue.add(getAnnotation(method, Permit.class));
        }

        if (method.isAnnotationPresent(PermissionContainer.class)) {
            PermissionContainer container = getAnnotation(method, PermissionContainer.class);
            queue.addAll(Arrays.asList(container.value()));
        }

        return queue;
    }

    private <A> A getAnnotation(Method method, Class<A> clazz) {
        A annotation = null;
        for (Annotation anno : method.getDeclaredAnnotations()) {
            if (anno.annotationType() == clazz) {
                annotation = (A) anno;
            }
        }

        return annotation;
    }

    @NonNull
    private PermanentDeniedHandler getPermanentDeniedHandler(Method method, Class<S> service) {
        Class<? extends PermanentDeniedHandler> clazz = null;

        if (method.isAnnotationPresent(PermanentDenied.class)) {
            clazz = method.getAnnotation(PermanentDenied.class).value();
        }
        else if (service.isAnnotationPresent(PermanentDenied.class)) {
            clazz = service.getAnnotation(PermanentDenied.class).value();
        }

        return clazz != null ? ObjectUtil.requireNonNull(initO(clazz)) : RuntimePermissionConfig.getInstance().getPermanentDeniedHandler();
    }

    @Nullable
    private PermissionFailureHandler getPermissionFailureHandler(Method method, Class<S> service) {
        Class<? extends PermissionFailureHandler> clazz = null;

        if (method.isAnnotationPresent(PermissionFailure.class)) {
            clazz = method.getAnnotation(PermissionFailure.class).value();
        }
        else if (service.isAnnotationPresent(PermissionFailure.class)) {
            clazz = service.getAnnotation(PermissionFailure.class).value();
        }

        return clazz != null ? initO(clazz) : RuntimePermissionConfig.getInstance().getPermissionFailureHandler();
    }

    private <O> O initO(Class<O> clazz) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (O) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void process(@NonNull ServiceMethod serviceMethod) {
        Permit permit = serviceMethod.getPermitQueue().poll();

        if (permit != null) {

            RuntimePermission.requestPermission(act, permit.value(), new PermissionCallback<FragmentActivity>() {
                @Override
                public void onGranted(Perm<FragmentActivity> perm) {
                    process(serviceMethod);
                }

                @Override
                public void onDenied(Perm<FragmentActivity> perm) {
                    if (permit.required()) {
                        serviceMethod.addToFailureList(permit);
                    }
                    process(serviceMethod);
                }

                @Override
                public void onPermanentDenied(Perm<FragmentActivity> perm) {
                    if (permit.required()) {
                        serviceMethod.addToFailureList(permit);
                    }

                    if (permit.checkPermanentDenied()) {
                        serviceMethod
                                .getPermanentDeniedHandler()
                                .onHandle(permit, perm.getCaller(), new SettingOpener() {
                                    @Override
                                    public void open() {
                                        RuntimePermission.openAppSettingIntent(perm.getCaller(), perm.getType(), new PermissionSettingCallback<FragmentActivity>() {
                                            @Override
                                            public void onGranted(@NonNull Perm<FragmentActivity> perm) {
                                                if (permit.required()) {
                                                    serviceMethod.removeToFailureList(permit);
                                                }
                                                process(serviceMethod);
                                            }

                                            @Override
                                            public void onDenied(@NonNull Perm<FragmentActivity> perm) {
                                                process(serviceMethod);
                                            }
                                        });
                                    }

                                    @Override
                                    public void doNothing() {
                                        process(serviceMethod);
                                    }
                                });
                    }
                    else {
                        process(serviceMethod);
                    }
                }
            });
        }
        else if (! serviceMethod.getFailurePermissionList().isEmpty()){
            PermissionFailureHandler failureHandler = serviceMethod.getPermissionFailureHandler();
            if (failureHandler != null) {
                failureHandler.onFailed(serviceMethod.getFailurePermissionList(), act, serviceMethod.getMethod());
            }
        }
        else {
            serviceMethod.getRequest().done();
        }
    }
}
