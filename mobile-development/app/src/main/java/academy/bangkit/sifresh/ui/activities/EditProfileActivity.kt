package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityEditProfileBinding
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPhoto()

        binding.apply {
            btnChangePhotoProfile.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                galleryLauncher.launch(chooser)
            }
            btnSave.setOnClickListener {
                // Save profile
            }
            btnCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun initPhoto(){
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImage: Uri = result.data?.data as Uri
                binding.ivProfilePhoto.setImageURI(selectedImage)
            }
        }
    }

}