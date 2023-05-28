package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityCameraBinding
import academy.bangkit.sifresh.utils.Helper
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import academy.bangkit.sifresh.R
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDialogInfo()
        initGallery()

        binding.apply {
            btnTakePhoto.setOnClickListener { takePhoto() }
            btnSwitch.setOnClickListener {
                cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
                startCamera()
            }
            btnGallery.setOnClickListener { startGallery() }
            btnInfo.setOnClickListener { setDialogInfo() }
            btnBack.setOnClickListener { finish() }
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun setDialogInfo() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_camera_info)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnStart : MaterialButton = dialog.findViewById(R.id.btn_dialog_camera_start)
        val textDescription : TextView = dialog.findViewById(R.id.tv_dialog_camera_infoText)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        btnStart.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Helper.createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent(this@CameraActivity, DetectResultActivity::class.java)
                    intent.putExtra(DetectResultActivity.EXTRA_PHOTO_RESULT, photoFile)
                    intent.putExtra(
                        DetectResultActivity.EXTRA_CAMERA_MODE,
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    this@CameraActivity.finish()
                    startActivity(intent)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        galleryLauncher.launch(chooser)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 720))
                .build()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(binding.previewCamera.surfaceProvider) }
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(480, 720))
                .build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis
                )
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun initGallery() {
        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.i(
                    "TEST_GALLERY",
                    "The gallery is successfully selected and will point to the new one"
                )
                val selectedImage: Uri = result.data?.data as Uri
                val myFile = Helper.uriToFile(selectedImage, this@CameraActivity)
                val intent = Intent(this@CameraActivity, DetectResultActivity::class.java)
                intent.putExtra(DetectResultActivity.EXTRA_PHOTO_RESULT, myFile)
                intent.putExtra(
                    DetectResultActivity.EXTRA_CAMERA_MODE,
                    cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                )
                this@CameraActivity.finish()
                startActivity(intent)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}