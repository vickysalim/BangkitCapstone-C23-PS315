package academy.bangkit.sifresh

import academy.bangkit.sifresh.databinding.FragmentRegisterInfoBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class RegisterInfoFragment : Fragment() {

    private lateinit var binding: FragmentRegisterInfoBinding

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        binding.apply {
            inputName.editText?.setText(viewModel.name)
            inputEmail.editText?.setText(viewModel.email)
            inputPassword.editText?.setText(viewModel.password)
            inputPhone.editText?.setText(viewModel.phoneNumber)
        }

        binding.btnNext.setOnClickListener {
            val registerAddressFragment = RegisterAddressFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frame_container, registerAddressFragment, RegisterAddressFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.name = binding.inputName.editText?.text.toString()
        viewModel.email = binding.inputEmail.editText?.text.toString()
        viewModel.password = binding.inputPassword.editText?.text.toString()
        viewModel.phoneNumber = binding.inputPhone.editText?.text.toString()
    }
}