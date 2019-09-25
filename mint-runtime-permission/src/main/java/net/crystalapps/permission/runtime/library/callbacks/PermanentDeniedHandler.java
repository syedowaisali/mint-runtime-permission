package net.crystalapps.permission.runtime.library.callbacks;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.annotations.Permit;

public interface PermanentDeniedHandler{
    void onHandle(@NonNull Permit permit, @NonNull FragmentActivity caller, @NonNull SettingOpener<FragmentActivity> settingOpener);
}