package net.crystalapps.permission.runtime.library.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.core.DynamicPermProxyInvocationHandler;
import net.crystalapps.permission.runtime.library.models.Perm;
import net.crystalapps.permission.runtime.library.core.PermProxyInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@SuppressWarnings("unchecked")
public class ProxyUtil {

    public static <T> Perm<T> getProxy(@NonNull String permType, int requestCode, @NonNull T caller) {
        return (Perm<T>) Proxy.newProxyInstance(Perm.class.getClassLoader(), new Class[]{Perm.class}, new PermProxyInvocationHandler<>(permType, requestCode, caller));
    }

    public static <T extends FragmentActivity, S> S getDynamicProxy(T act, Class<S> service) {
        return (S) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new DynamicPermProxyInvocationHandler<>(act, service));
    }
}
