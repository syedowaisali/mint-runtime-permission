package net.crystalapps.permission.runtime.library.config;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import net.crystalapps.permission.runtime.library.annotations.Permit;
import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler;
import net.crystalapps.permission.runtime.library.callbacks.SettingOpener;
import net.crystalapps.permission.runtime.library.utils.StringUtil;

public class MintPermanentDeniedHandler implements PermanentDeniedHandler {

    @Override
    public void onHandle(@NonNull Permit permit, @NonNull FragmentActivity caller, @NonNull SettingOpener<FragmentActivity> settingOpener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(caller);
        builder.setMessage(StringUtil.parseMessage(caller, permit.whenPermanentDenied(), "Require " + permit.value() + " permission to further process"));
        builder.setPositiveButton("OPEN SETTINGS", (dialogInterface, i) -> settingOpener.open(caller, permit.value()));
        builder.setNegativeButton("NO", (dialogInterface, i) -> settingOpener.doNothing(caller, permit.value()));
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}