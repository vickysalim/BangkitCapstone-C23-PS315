package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.ui.viewmodels.RegisterViewModel
import academy.bangkit.sifresh.databinding.FragmentRegisterInfoBinding
import academy.bangkit.sifresh.utils.ResponseCode
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

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
            inputName.editText?.setText(viewModel.name.value.toString())
            inputEmail.editText?.setText(viewModel.email.value.toString())
            inputPassword.editText?.setText(viewModel.password.value.toString())
            inputPhone.editText?.setText(viewModel.phoneNumber.value.toString())
        }

        binding.btnNext.setOnClickListener {
            binding.apply {
                val name = inputName.editText?.text.toString()
                val email = inputEmail.editText?.text.toString()
                val password = inputPassword.editText?.text.toString()
                val phoneNumber = inputPhone.editText?.text.toString()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all the fields required",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(
                        requireContext(),
                        "Email format is invalid!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.isEmailValid(email)
                }
            }
        }

        viewModel.emailValidStatus.observe(viewLifecycleOwner) {
            when (it) {
                ResponseCode.LOADING -> {
                    binding.btnNext.isEnabled = false
                }
                ResponseCode.NOT_FOUND -> {
                    binding.btnNext.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Email already registered!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ResponseCode.SUCCESS -> {
                    binding.btnNext.isEnabled = true
                    val registerAddressFragment = RegisterAddressFragment()
                    val fragmentManager = parentFragmentManager
                    fragmentManager.beginTransaction()
                        .replace(
                            R.id.frame_container,
                            registerAddressFragment,
                            RegisterAddressFragment::class.java.simpleName
                        )
                        .addToBackStack(null)
                        .commit()
                    viewModel.emailValidStatus.value = ResponseCode.NOTHING
                }
                else -> {
                    binding.btnNext.isEnabled = true
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.name.value = binding.inputName.editText?.text.toString()
        viewModel.email.value = binding.inputEmail.editText?.text.toString()
        viewModel.password.value = binding.inputPassword.editText?.text.toString()
        viewModel.phoneNumber.value = binding.inputPhone.editText?.text.toString()
    }
}