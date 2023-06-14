package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.databinding.FragmentRegisterAddressBinding
import academy.bangkit.sifresh.ui.viewmodels.RegisterViewModel
import academy.bangkit.sifresh.utils.ResponseCode
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

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

        viewModel.getProvince()
        viewModel.provincesList.observe(viewLifecycleOwner) {
            val provinceNames = it.map { province -> province.name }
            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, provinceNames)

            binding.actvProvince.setAdapter(adapter)
            binding.actvProvince.keyListener = null

            binding.actvProvince.setOnItemClickListener { _, _, position, _ ->
                binding.actvCity.setText("")
                binding.actvDistrict.setText("")

                val selectedProvince = it[position]
                val provinceId = selectedProvince.id
                viewModel.getCity(provinceId)
            }
        }

        viewModel.citiesList.observe(viewLifecycleOwner) {
            val cityNames = it.map { city -> city.name }
            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, cityNames)

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

        viewModel.districtsList.observe(viewLifecycleOwner) {
            val districtNames = it.map { district -> district.name }
            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, districtNames)

            binding.actvDistrict.setAdapter(adapter)
            binding.actvDistrict.keyListener = null
        }

        binding.apply {
            inputProvince.editText?.setText(viewModel.province.value.toString())
            inputCity.editText?.setText(viewModel.city.value.toString())
            inputDistrict.editText?.setText(viewModel.district.value.toString())
            inputPostalCode.editText?.setText(viewModel.postalCode.value.toString())
            inputAddress.editText?.setText(viewModel.address.value.toString())
        }

        binding.btnPrevious.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.registerUser()

            viewModel.province.value = binding.inputProvince.editText?.text.toString()
            viewModel.city.value = binding.inputCity.editText?.text.toString()
            viewModel.district.value = binding.inputDistrict.editText?.text.toString()
            viewModel.postalCode.value = binding.inputPostalCode.editText?.text.toString()
            viewModel.address.value = binding.inputAddress.editText?.text.toString()
        }

        viewModel.status.observe(viewLifecycleOwner) {
            setBehavior(it)
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.province.value = binding.inputProvince.editText?.text.toString()
        viewModel.city.value = binding.inputCity.editText?.text.toString()
        viewModel.district.value = binding.inputDistrict.editText?.text.toString()
        viewModel.postalCode.value = binding.inputPostalCode.editText?.text.toString()
        viewModel.address.value = binding.inputAddress.editText?.text.toString()
    }

    private fun setBehavior(responseCode: Int) {
        when (responseCode) {
            ResponseCode.SUCCESS -> {
                Toast.makeText(
                    requireContext(),
                    getString(academy.bangkit.sifresh.R.string.text_register_success),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
            ResponseCode.NOT_FOUND -> {
                Toast.makeText(
                    requireContext(),
                    getString(academy.bangkit.sifresh.R.string.text_email_exists),
                    Toast.LENGTH_SHORT
                ).show()
            }
            ResponseCode.FORBIDDEN -> {
                Toast.makeText(
                    requireContext(),
                    getString(academy.bangkit.sifresh.R.string.text_forbidden),
                    Toast.LENGTH_SHORT
                ).show()
            }
            ResponseCode.SERVER_TIMEOUT -> {
                Toast.makeText(
                    requireContext(),
                    getString(academy.bangkit.sifresh.R.string.text_timeout),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}