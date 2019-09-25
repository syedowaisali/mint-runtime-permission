package net.crystalapps.permission.runtime.library.callbacks;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public interface SettingOpener<T extends FragmentActivity>{
    void open(@NonNull T caller, @NonNull String permission);
    void doNothing(@NonNull T caller, @NonNull String permission);
}