package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.ActivityEditProfileBinding
import academy.bangkit.sifresh.ui.viewmodels.EditProfileViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import academy.bangkit.sifresh.utils.ResponseCode
import academy.bangkit.sifresh.utils.uriToFile
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private lateinit var viewModel: EditProfileViewModel

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]

        setUserData()

        binding.apply {
            actvCity.isEnabled = false
            actvDistrict.isEnabled = false
        }

        viewModel.userId.observe(this) {
            viewModel.getUserData()
        }

        viewModel.userDataStatus.observe(this) {
            when (it) {
                ResponseCode.LOADING -> {
                    binding.apply {
                        inputName.editText?.setText("Loading...")
                        inputProvince.editText?.setText("Loading...")
                        inputCity.editText?.setText("Loading...")
                        inputDistrict.editText?.setText("Loading...")
                        inputAddress.editText?.setText("Loading...")
                        inputPostalCode.editText?.setText("Loading...")
                        btnSave.isEnabled = false

                    }
                }
                ResponseCode.SUCCESS -> {
                    binding.apply {
                        viewModel.user.observe(this@EditProfileActivity) { user ->
                            Log.e("USER", user.toString())
                            Glide.with(this@EditProfileActivity)
                                .load(user?.profilePicUrl)
                                .into(binding.ivProfilePhoto)
                            inputName.editText?.setText(user?.fullName.toString())
                            inputProvince.editText?.setText(user?.province.toString())
                            inputCity.editText?.setText(user?.city.toString())
                            inputDistrict.editText?.setText(user?.kecamatan.toString())
                            inputAddress.editText?.setText(user?.address.toString())
                            inputPostalCode.editText?.setText(user?.kodePos.toString())
                            btnSave.isEnabled = true
                        }
                    }
                }
                else -> {
                    Toast.makeText(
                        this,
                        academy.bangkit.sifresh.R.string.text_forbidden,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        viewModel.getProvince()
        viewModel.provincesList.observe(this) {
            val provinceNames = it.map { province -> province.name }
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, provinceNames)

            binding.actvProvince.setAdapter(adapter)
            binding.actvProvince.keyListener = null

            binding.actvProvince.setOnItemClickListener { _, _, position, _ ->
                binding.actvCity.setText("")
                binding.actvDistrict.setText("")

                binding.actvCity.isEnabled = true
                binding.actvDistrict.isEnabled = true

                val selectedProvince = it[position]
                val provinceId = selectedProvince.id
                viewModel.getCity(provinceId)
            }
        }

        viewModel.citiesList.observe(this) {
            val cityNames = it.map { city -> city.name }
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cityNames)

            binding.actvCity.setAdapter(adapter)
            binding.actvCity.keyListener = null

            binding.actvCity.setOnItemClickListener { _, _, position, _ ->
                binding.actvDistrict.setText("")

                val selectedCity = it[position]
                val provinceId = selectedCity.provinceId
                val cityId = selectedCity.id
                viewModel.getDistrict(provinceId, cityId)
            }
        }

        viewModel.districtsList.observe(this) {
            val districtNames = it.map { district -> district.name }
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, districtNames)

            binding.actvDistrict.setAdapter(adapter)
            binding.actvDistrict.keyListener = null
        }

        viewModel.updateDataStatus.observe(this) {
            when (it) {
                ResponseCode.NOTHING -> {
                    binding.btnSave.isEnabled = true
                }
                ResponseCode.LOADING -> {
                    binding.btnSave.isEnabled = false
                }
                ResponseCode.SUCCESS -> {
                    Toast.makeText(
                        this,
                        academy.bangkit.sifresh.R.string.text_update_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                else -> {
                    Toast.makeText(
                        this,
                        academy.bangkit.sifresh.R.string.text_update_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSave.isEnabled = true
                }
            }
        }

        initPhoto()

        viewModel.file.observe(this) {
            getFile = it
            Glide.with(binding.ivProfilePhoto).load(BitmapFactory.decodeFile(it.path))
                .into(binding.ivProfilePhoto)
        }

        binding.apply {
            btnChangePhotoProfile.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                galleryLauncher.launch(chooser)
            }
            btnSave.setOnClickListener {
                Log.e("Get File", getFile.toString())
                if (getFile != null) {
                    val id = viewModel.userId.value.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())

                    val file = getFile!!

                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part =
                        MultipartBody.Part.createFormData("photo", file.name, requestFile)

                    viewModel.uploadPhoto(id, imageMultipart)
                }

                val name = inputName.editText?.text.toString()
                val province = inputProvince.editText?.text.toString()
                val city = inputCity.editText?.text.toString()
                val district = inputDistrict.editText?.text.toString()
                val address = inputAddress.editText?.text.toString()
                val postalCode = inputPostalCode.editText?.text.toString()
                viewModel.saveUserData(name, province, city, district, address, postalCode)
            }
            btnCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun initPhoto() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    viewModel.file.value =
                        uriToFile(result.data?.data as Uri, this@EditProfileActivity)
                }
            }
    }

    private fun setUserData() {
        val settingPreferences = SettingPreferences.getInstance(applicationContext.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreferences)
        )[SettingViewModel::class.java]

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserID.name)
            .observe(this) { id ->
                if (id != "") viewModel.userId.value = id
            }

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserToken.name)
            .observe(this) { token ->
                if (token != "") viewModel.userToken.value = token
            }
    }

}