package net.crystalapps.permission.runtime.library.callbacks;

import net.crystalapps.permission.runtime.library.models.Perm;

/**
 * Created by Syed Owais Ali on 5/8/2018.
 */

public interface PermissionCallback<T> {
    void onGranted(Perm<T> perm);
    void onDenied(Perm<T> perm);
    void onPermanentDenied(Perm<T> perm);
}
