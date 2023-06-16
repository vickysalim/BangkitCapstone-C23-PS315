package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.ActivityAddProductBinding
import academy.bangkit.sifresh.ui.viewmodels.ProductViewModel
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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private val productViewModel by viewModels<ProductViewModel>()
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserData()
        observe()
        initPhoto()

        productViewModel.file.observe(this) {
            getFile = it
            Glide.with(binding.ivProduct).load(BitmapFactory.decodeFile(it.path))
                .into(binding.ivProduct)
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            val typeList = listOf("fruit", "vegetable")
            actvType.setAdapter(
                ArrayAdapter(
                    this@AddProductActivity,
                    android.R.layout.simple_dropdown_item_1line,
                    typeList
                )
            )
            actvType.keyListener = null
            actvType.setOnItemClickListener { _, _, position, _ ->
                val selectedType = typeList[position]
                productViewModel.productType.value = selectedType
            }

            btnAddImage.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                galleryLauncher.launch(chooser)
            }
            btnSubmit.setOnClickListener {
                Log.e("Get File", getFile.toString())
                //if (getFile != null) {
                    // val id = productViewModel.sellerId.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    // val file = getFile!!

                    // val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    // val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestFile)

                    val name = inputName.editText?.text.toString()
                    val type = inputType.editText?.text.toString()
                    val price = inputPrice.editText?.text.toString()
                    val description = tilDescription.editText?.text.toString()
                    /*val publishedAt = SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        Locale.getDefault()
                    ).format(
                        Date()
                    )*/


                    productViewModel.addProduct(
                        name,
                        type,
                        price.toDouble(),
                        true,
                        description,
                        //publishedAt.toString()
                    )
                    finish()
                //}
            }
        }
    }

    private fun initPhoto() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    productViewModel.file.value =
                        uriToFile(result.data?.data as Uri, this@AddProductActivity)
                }
            }
    }

    private fun observe() {
        productViewModel.addProduct.observe(this) {
            when (it) {
                ResponseCode.NOTHING -> {
                    binding.btnSubmit.isEnabled = true
                }
                ResponseCode.LOADING -> {
                    binding.btnSubmit.isEnabled = false
                }
                ResponseCode.SUCCESS -> {
                    Toast.makeText(this, "Add Product Success!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Add Product Failed!", Toast.LENGTH_SHORT).show()
                    binding.btnSubmit.isEnabled = true
                }
            }
        }
    }

    private fun setUserData() {
        val settingPreferences = SettingPreferences.getInstance(this.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreferences)
        )[SettingViewModel::class.java]

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserID.name)
            .observe(this) { sellerId ->
                if (sellerId != "") productViewModel.sellerId.value = sellerId
            }

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserToken.name)
            .observe(this) { token ->
                if (token != "") productViewModel.userToken.value = token
                Log.d("Token", productViewModel.userToken.value.toString())
            }

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserName.name)
            .observe(this) { sellerName ->
                if (sellerName != "") productViewModel.sellerName.value = sellerName
                Log.d("Token", productViewModel.sellerName.value.toString())
            }
    }
}