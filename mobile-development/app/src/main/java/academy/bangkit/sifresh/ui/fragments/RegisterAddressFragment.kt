package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.data.response.AddAddress
import academy.bangkit.sifresh.data.response.Register
import academy.bangkit.sifresh.data.response.User
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.databinding.FragmentRegisterAddressBinding
import academy.bangkit.sifresh.ui.viewmodels.RegisterViewModel
import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class RegisterAddressFragment : Fragment() {

    private lateinit var binding: FragmentRegisterAddressBinding

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        val client = ApiConfig.getApiService().checkEmail("vickysalim@mhs.mdp.ac.id")

        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        Log.d("RegisterViewModel", "onResponse: ${result.email}")
                    }
                }
            }

            override fun onFailure(
                call: Call<User>, t: Throwable
            ) {
                Log.e("RegisterViewModel", "onFailed: ${t.message}")
            }
        })

        val provinceItems = listOf("Sumatera Selatan", "Sulawesi Selatan", "DKI Jakarta")
        val cityItems = listOf("Palembang", "Makassar", "Jakarta Selatan")
        val districtItems = listOf("Kecamatan ABC", "Kecamatan XYZ")

        setDropdownAdapter(provinceItems, binding.actvProvince)
        setDropdownAdapter(cityItems, binding.actvCity)
        setDropdownAdapter(districtItems, binding.actvDistrict)

        binding.apply {
            inputProvince.editText?.setText(viewModel.province)
            inputCity.editText?.setText(viewModel.city)
            inputDistrict.editText?.setText(viewModel.district)
            inputPostalCode.editText?.setText(viewModel.postalCode)
            inputAddress.editText?.setText(viewModel.address)
        }

        binding.btnPrevious.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnRegister.setOnClickListener {
//            GlobalScope.launch(Dispatchers.IO) {
//                val url = URL("https://storage.googleapis.com/sifresh-bucket-one/uploads/65d09804-e496-445c-b1d3-010b1f827f56.png")
//                val connection = url.openConnection()
//                connection.doInput = true
//                connection.connect()
//                val inputStream = connection.getInputStream()
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//
//                // Create a file to store the bitmap
//                val file = File(requireContext().filesDir, "your_file.png")
//
//                try {
//                    file.createNewFile()
//                    val outputStream = FileOutputStream(file)
//
//                    // Compress the bitmap to PNG format and write it to the file
//                    launch(Dispatchers.IO) {
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                        outputStream.flush()
//                        outputStream.close()
//                    }
//
//                    // File conversion completed successfully, you can perform further actions here if needed

                    val client = ApiConfig.getApiService().addAddress(
                        "2b720aa2-9357-43f3-9a49-09af1a945150",
                    "Jl. Jalan",
                    "SUMATERA SELATAN",
                    "PALEMBANG",
                    "ILIR TIMUR III",
                    "12345",
                    )

                    client.enqueue(object : Callback<AddAddress> {
                        override fun onResponse(
                            call: Call<AddAddress>,
                            response: Response<AddAddress>
                        ) {
                            if (response.isSuccessful) {
                                val result = response.body()
                                if (result != null) {
                                    Log.d("RegisterViewModel", "onResponse: ${result}")
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<AddAddress>, t: Throwable
                        ) {
                            Log.e("RegisterViewModel", "onFailed: ${t.message}")
                        }
                    })

//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    // Handle any exceptions that may occur during file creation or writing
//                }
//            }
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.province = binding.inputProvince.editText?.text.toString()
        viewModel.city = binding.inputCity.editText?.text.toString()
        viewModel.district = binding.inputDistrict.editText?.text.toString()
        viewModel.postalCode = binding.inputPostalCode.editText?.text.toString()
        viewModel.address = binding.inputAddress.editText?.text.toString()
    }

    private fun setDropdownAdapter(items: List<String>, actv: MaterialAutoCompleteTextView) {
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, items)

        actv.setAdapter(adapter)
        actv.keyListener = null
    }
}