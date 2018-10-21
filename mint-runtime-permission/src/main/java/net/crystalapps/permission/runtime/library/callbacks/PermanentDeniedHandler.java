package net.crystalapps.permission.runtime.library.callbacks;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.annotations.Permit;

public interface PermanentDeniedHandler{
    void onHandle(@NonNull Permit permit, @NonNull FragmentActivity caller, @NonNull SettingOpener<FragmentActivity> settingOpener);
}