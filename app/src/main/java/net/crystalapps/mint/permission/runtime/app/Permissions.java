package net.crystalapps.mint.permission.runtime.app;

import android.Manifest;

import net.crystalapps.permission.runtime.library.annotations.Permit;
import net.crystalapps.permission.runtime.library.callbacks.Request;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

public interface Permissions {

    @Permit(Manifest.permission.CAMERA)
    void openCamera(Request request);

    @Permit(value = Manifest.permission.WRITE_EXTERNAL_STORAGE, whenDenied = R.string.storage_perm_denied, whenPermanentDenied = R.string.storage_perm_permanent_denied)
    void openStorage(Request request);

    @Permit(value = Manifest.permission.SEND_SMS, whenDenied = R.string.sms_perm_denied, whenPermanentDenied = R.string.sms_perm_permanent_denied)
    void sendSMS(Request request);

    @Permit(value = Manifest.permission.RECORD_AUDIO, whenDenied = R.string.audio_perm_denied, whenPermanentDenied = R.string.audio_perm_permanent_denied)
    void recordAudio(Request request);
}
