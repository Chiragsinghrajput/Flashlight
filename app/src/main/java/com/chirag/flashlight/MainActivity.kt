package com.chirag.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var toggleButton: ToggleButton
    private lateinit var cameraManager: CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton = findViewById(R.id.toggleButton)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        toggleButton.setOnClickListener {
            if (toggleButton.isChecked) {
                turnOnFlashlight()
            } else {
                turnOffFlashlight()
            }
        }

        checkForFlashlight()
    }

    private fun checkForFlashlight() {
        if (cameraManager.cameraIdList.isEmpty()) {
            toggleButton.isEnabled = false
            toggleButton.text = "No flashlight available"
        }
    }

    private fun turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraManager.cameraIdList[0], true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraManager.cameraIdList[0], false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        turnOffFlashlight()
    }
}