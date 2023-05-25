package academy.bangkit.sifresh

import academy.bangkit.sifresh.databinding.FragmentRegisterAddressBinding
import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView

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