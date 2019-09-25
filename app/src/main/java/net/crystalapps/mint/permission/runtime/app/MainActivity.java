package net.crystalapps.mint.permission.runtime.app;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import net.crystalapps.permission.runtime.library.core.RuntimePermission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Permissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissions = RuntimePermission.ask(this, Permissions.class);

        // attach camera button listener
        attachListener(R.id.btn_get_camera_perm);
        attachListener(R.id.btn_get_storage_perm);
        attachListener(R.id.btn_get_sms_perm);
        attachListener(R.id.btn_get_audio_perm);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_get_camera_perm:
                RuntimePermission.requestPermission(this, Manifest.permission.CAMERA, new CameraPermissionResult());
                break;

            case R.id.btn_get_storage_perm:
                permissions.openStorage(this::writeFile);
                break;

            case R.id.btn_get_sms_perm:
                permissions.sendSMS(this::sendingSMS);
                break;

            case R.id.btn_get_audio_perm:
                permissions.recordAudio(this::recordingStart);
                break;
        }
    }

    private void writeFile() {
        Toast.makeText(this, "Writing files.", Toast.LENGTH_LONG).show();
    }

    private void sendingSMS() {
        Toast.makeText(this, "SMS Sending.", Toast.LENGTH_LONG).show();
    }

    private void recordingStart() {
        Toast.makeText(this, "Audio recording start.", Toast.LENGTH_LONG).show();
    }

    private void attachListener(@IdRes int id) {
        findViewById(id).setOnClickListener(this);
    }
}
