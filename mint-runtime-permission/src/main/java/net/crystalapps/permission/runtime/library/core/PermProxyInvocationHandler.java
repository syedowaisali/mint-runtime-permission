package net.crystalapps.permission.runtime.library.core;

import android.support.annotation.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public class PermProxyInvocationHandler<T> implements InvocationHandler {

    @NonNull
    private final String type;
    private final int requestCode;
    @NonNull
    private final T caller;

    public PermProxyInvocationHandler(@NonNull String type, int requestCode, @NonNull T caller) {
        this.type = type;
        this.requestCode = requestCode;
        this.caller = caller;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) {

        switch (method.getName()) {
            case "getType":
                return type;
            case "getRequestCode":
                return requestCode;
            case "getCaller":
                return caller;

            default:
                throw new UnsupportedOperationException("Unsupported method: " + method.getName());
        }
    }
}
