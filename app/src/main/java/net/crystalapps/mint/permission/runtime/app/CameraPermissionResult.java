package net.crystalapps.mint.permission.runtime.app;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import net.crystalapps.permission.runtime.library.models.Perm;
import net.crystalapps.permission.runtime.library.callbacks.PermissionCallback;
import net.crystalapps.permission.runtime.library.callbacks.PermissionSettingCallback;
import net.crystalapps.permission.runtime.library.core.RuntimePermission;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */
public class CameraPermissionResult implements PermissionCallback<MainActivity>{

    @Override
    public void onGranted(Perm<MainActivity> perm) {
        perm.getCaller().startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 19);
    }

    @Override
    public void onDenied(Perm<MainActivity> perm) {
        Toast.makeText(perm.getCaller(), "Permission denied", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermanentDenied(Perm<MainActivity> perm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(perm.getCaller());
        builder.setMessage("Allow camera permission in app detail option.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            RuntimePermission.openAppSettingIntent(perm.getCaller(), perm.getType(), new SettingCallback());
        });
        builder.create().show();
    }

    private class SettingCallback implements PermissionSettingCallback<MainActivity> {

        @Override
        public void onGranted(@NonNull Perm<MainActivity> perm) {
            perm.getCaller().startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }

        @Override
        public void onDenied(@NonNull Perm<MainActivity> perm) {
            Toast.makeText(perm.getCaller(), "Permission denied from settings", Toast.LENGTH_LONG).show();
        }
    }
}
