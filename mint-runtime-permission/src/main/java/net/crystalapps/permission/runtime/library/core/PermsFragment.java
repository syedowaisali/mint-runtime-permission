package net.crystalapps.permission.runtime.library.core;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback;
import net.crystalapps.permission.runtime.library.models.Perm;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public class PermsFragment<T> extends Fragment {

    private Perm<T> perm;
    private PermissionCallback<T> callback;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == perm.getRequestCode()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onGranted(perm);
            } else {
                callback.onDenied(perm);
            }

            // remove fragment from manager
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit();
            }
        }
    }

    public static <T> PermsFragment getInstance(Perm<T> perm, PermissionCallback<T> callback) {
        PermsFragment<T> fragment = new PermsFragment<>();
        fragment.perm = perm;
        fragment.callback = callback;
        return fragment;
    }
}