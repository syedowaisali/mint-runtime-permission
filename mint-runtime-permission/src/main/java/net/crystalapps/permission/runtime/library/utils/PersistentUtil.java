package net.crystalapps.permission.runtime.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public class PersistentUtil {

    public static boolean isCameFirst(@NonNull Context context, @NonNull String permType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mint_core_rp", Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean(permType, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(permType, false);
        editor.apply();
        return isFirstTime;
    }
}
