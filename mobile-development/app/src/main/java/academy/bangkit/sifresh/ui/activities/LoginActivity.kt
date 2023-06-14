package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.ActivityLoginBinding
import academy.bangkit.sifresh.ui.viewmodels.LoginViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSettingPreferences()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            when {
                email.isEmpty() or password.isEmpty() -> {
                    binding.inputEmail.requestFocus()
                    Toast.makeText(
                        this,
                        "Email and Password must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+")) -> {
                    binding.inputEmail.requestFocus()
                    Toast.makeText(
                        this,
                        "Email format is invalid!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    viewModel.login(email, password)
                }
            }
        }
    }

    private fun setUpSettingPreferences() {
        val settingPreferences = SettingPreferences.getInstance(applicationContext.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreferences)
        )[SettingViewModel::class.java]

        viewModel.loginResult.observe(this) { login ->
            settingViewModel.setUserPreferences(
                login.jwt,
                login.cred.id,
                login.cred.fullName,
                viewModel.tempEmail.value ?: "",
            )
        }

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserToken.name)
            .observe(this) { token ->
//                if (token != "") startActivity(Intent(this, MainActivity::class.java))
                if (token != "") startActivity(Intent(this, MainActivity::class.java))
            }
    }
}