package net.crystalapps.permission.runtime.library.core;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback;
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback;
import net.crystalapps.permission.runtime.library.models.Perm;
import net.crystalapps.permission.runtime.library.utils.ObjectUtil;
import net.crystalapps.permission.runtime.library.utils.PersistentUtil;
import net.crystalapps.permission.runtime.library.utils.ProxyUtil;

/**
 * Created by Syed Owais Ali on 5/7/2018.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class RuntimePermission {

    public static boolean isPermissionRequired() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasPermission(@NonNull Context context, String perm) {
        return !isPermissionRequired() || ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED;
    }

    public static <T extends Fragment> void requestPermission(@NonNull T caller, String permType, @NonNull PermissionCallback<T> callback) {
        requestPermission(caller, permType, 1947, callback);
    }

    public static <T extends Fragment> void requestPermission(@NonNull T caller, String permType, int requestCode, @NonNull PermissionCallback<T> callback) {
        // noinspection ConstantConditions
        requestPermission(caller.getActivity(), caller, permType, 1947, callback);
    }

    public static <T extends FragmentActivity> void requestPermission(@NonNull T caller, String permType, @NonNull PermissionCallback<T> callback) {
        requestPermission(caller, permType, 1947, callback);
    }

    public static <T extends FragmentActivity> void requestPermission(@NonNull T caller, @NonNull String permType, int requestCode, @NonNull PermissionCallback<T> callback) {
        requestPermission(caller, caller, permType, requestCode, callback);
    }

    private static <T> void requestPermission(@NonNull FragmentActivity activity, @NonNull T caller, @NonNull String permType, int requestCode, @NonNull PermissionCallback<T> callback) {

        // null check
        ObjectUtil.requireNonNull(caller, "caller cannot be null");
        ObjectUtil.requireNonNull(permType, "permission cannot be null");
        ObjectUtil.requireNonNull(callback, "callback cannot be null");

        final Perm<T> perm = ProxyUtil.getProxy(permType, requestCode, caller);

        if (isPermissionRequired() && !hasPermission(activity, permType)) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, perm.getType()) && !PersistentUtil.isCameFirst(activity, perm.getType())) {
                callback.onPermanentDenied(perm);
            } else {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                PermsFragment fragment = PermsFragment.getInstance(perm, callback);
                fragmentManager.beginTransaction().add(fragment, "PERMS_FRAGMENT").commitNow();
                //fragmentManager.executePendingTransactions();
                fragment.requestPermissions(new String[]{perm.getType()}, perm.getRequestCode());
            }
        } else {
            callback.onGranted(perm);
        }
    }

    public static <T extends Fragment> void openAppSettingIntent(@NonNull T caller, @NonNull String permType, @NonNull PermissionSettingCallback<T> callback) {
        openAppSettingIntent(caller, permType, 148, callback);
    }

    public static <T extends Fragment> void openAppSettingIntent(@NonNull T caller, @NonNull String permType, int requestCode, @NonNull PermissionSettingCallback<T> callback) {
        // noinspection ConstantConditions
        openAppSettingIntent(caller.getActivity(), caller, permType, requestCode, callback);
    }

    public static <T extends FragmentActivity> void openAppSettingIntent(@NonNull T caller, @NonNull String permType, @NonNull PermissionSettingCallback<T> callback) {
        openAppSettingIntent(caller, permType, 148, callback);
    }

    public static <T extends FragmentActivity> void openAppSettingIntent(@NonNull T caller, @NonNull String permType, int requestCode, @NonNull PermissionSettingCallback<T> callback) {
        openAppSettingIntent(caller, caller, permType, requestCode, callback);
    }

    private static <T> void openAppSettingIntent(@NonNull FragmentActivity activity, @NonNull T caller, @NonNull String permType, int requestCode, @NonNull PermissionSettingCallback<T> callback) {

        // null check
        ObjectUtil.requireNonNull(caller, "caller cannot be null");
        ObjectUtil.requireNonNull(permType, "permission cannot be null");
        ObjectUtil.requireNonNull(callback, "callback cannot be null");

        final Perm<T> perm = ProxyUtil.getProxy(permType, requestCode, caller);

        if (isPermissionRequired()) {

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            AppSettingFragment fragment = AppSettingFragment.getInstance(perm, callback);
            fragmentManager.beginTransaction().add(fragment, "APP_SETTING_FRAGMENT").commitNow();
            //fragmentManager.executePendingTransactions();

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.getPackageName(), null));
            fragment.startActivityForResult(intent, perm.getRequestCode());
        } else {
            callback.onGranted(perm);
        }
    }

    public static <T extends Fragment, S> S ask(@NonNull T caller, @NonNull Class<S> service) {
        // noinspection ConstantConditions
        return ask(caller.getActivity(), service);
    }

    public static <T extends FragmentActivity, S> S ask(@NonNull T caller, @NonNull Class<S> service) {
        return ProxyUtil.getDynamicProxy(caller, service);
    }
}
