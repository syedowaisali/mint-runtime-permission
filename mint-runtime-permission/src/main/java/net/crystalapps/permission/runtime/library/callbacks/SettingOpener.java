package net.crystalapps.permission.runtime.library.callbacks;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.models.Perm;

public interface SettingOpener<T extends FragmentActivity>{
    void open(@NonNull T caller, @NonNull String permission);
    void doNothing(@NonNull T caller, @NonNull String permission);
}