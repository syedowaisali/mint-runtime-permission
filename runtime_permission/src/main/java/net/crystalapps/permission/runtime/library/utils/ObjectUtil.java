package net.crystalapps.permission.runtime.library.utils;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
@SuppressWarnings("WeakerAccess")
public class ObjectUtil {

    public static <T> T requireNonNull(T obj) {
        return requireNonNull(obj, null);
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }
}
