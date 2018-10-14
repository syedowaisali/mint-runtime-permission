package net.crystalapps.permission.runtime.library.callbacks;

import android.support.annotation.NonNull;

import net.crystalapps.permission.runtime.library.models.Perm;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public interface PermissionSettingCallback<T> {
    void onGranted(@NonNull Perm<T> perm);
    void onDenied(@NonNull Perm<T> perm);
}
