package net.crystalapps.permission.runtime.library.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by Syed Owais Ali on 10/14/2018.
 */
public class StringUtil {

    public static String parseMessage(@NonNull Context context, @StringRes int resId, @Nullable String defaultValue) {
        if (resId != -1) {
            try {
                return context.getResources().getString(resId);
            }
            catch (Exception ex) {
                return defaultValue;
            }
        }
        else {
            return defaultValue;
        }
    }
}