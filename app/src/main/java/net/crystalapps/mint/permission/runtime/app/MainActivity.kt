package net.crystalapps.mint.permission.runtime.app

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import net.crystalapps.permission.runtime.library.callbacks.Request

import net.crystalapps.permission.runtime.library.core.RuntimePermission

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var permissions: Permissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissions = RuntimePermission.ask(this, Permissions::class.java)

        // attach camera button listener
        attachListener(R.id.btn_get_camera_perm)
        attachListener(R.id.btn_get_storage_perm)
        attachListener(R.id.btn_get_sms_perm)
        attachListener(R.id.btn_get_audio_perm)

    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.btn_get_camera_perm -> RuntimePermission.requestPermission(this, Manifest.permission.CAMERA, CameraPermissionResult())

            R.id.btn_get_storage_perm -> permissions!!.openStorage{ this.writeFile() }

            R.id.btn_get_sms_perm -> permissions!!.sendSMS{ this.sendingSMS() }

            R.id.btn_get_audio_perm -> permissions!!.recordAudio{ this.recordingStart() }
        }
    }

    private fun writeFile() {
        Toast.makeText(this, "Writing files.", Toast.LENGTH_LONG).show()
    }

    private fun sendingSMS() {
        Toast.makeText(this, "SMS Sending.", Toast.LENGTH_LONG).show()
    }

    private fun recordingStart() {
        Toast.makeText(this, "Audio recording start.", Toast.LENGTH_LONG).show()
    }

    private fun attachListener(@IdRes id: Int) {
        findViewById<View>(id).setOnClickListener(this)
    }
}
