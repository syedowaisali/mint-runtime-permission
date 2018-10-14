package net.crystalapps.permission.runtime.library.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.crystalapps.permission.runtime.library.models.Perm;
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback;

import java.util.Objects;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public class AppSettingFragment<T> extends Fragment {

    private Perm<T> perm;
    private PermissionSettingCallback<T> callback;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RuntimePermission.hasPermission(Objects.requireNonNull(getContext()), perm.getType())) {
            callback.onGranted(perm);
        } else {
            callback.onDenied(perm);
        }

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(this).commitAllowingStateLoss();
        }
    }

    public static <T> AppSettingFragment getInstance(@NonNull Perm<T> perm, @NonNull PermissionSettingCallback<T> callback) {
        AppSettingFragment<T> fragment = new AppSettingFragment<>();
        fragment.perm = perm;
        fragment.callback = callback;
        return fragment;
    }
}