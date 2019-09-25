package net.crystalapps.permission.runtime.library.callbacks;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.annotations.Permit;

import java.lang.reflect.Method;
import java.util.List;

public interface PermissionFailureHandler {
    void onFailed(@NonNull List<Permit> failedPermissionList, @NonNull FragmentActivity caller, @NonNull Method method);
}