package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.FragmentProfileBinding
import academy.bangkit.sifresh.ui.activities.AddProductActivity
import academy.bangkit.sifresh.ui.activities.EditProfileActivity
import academy.bangkit.sifresh.ui.activities.LoginActivity
import academy.bangkit.sifresh.ui.activities.MainActivity
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import java.util.Locale

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnEditProfile.setOnClickListener {
                // Go to Edit Profile
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }
            btnAddProduct.setOnClickListener {
                // Go to Add Product
                val intent = Intent(requireContext(), AddProductActivity::class.java)
                startActivity(intent)
            }
            btnLogOut.setOnClickListener {
                // Log out (Back to Login Activity)
                (activity as MainActivity).logOut()
            }
            settingsGroup.tvLanguageDisplay.text =
                Locale.getDefault().getDisplayLanguage(Locale.getDefault())
            settingsGroup.btnChangeLanguage.setOnClickListener {
                // Change Language
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                settingsGroup.tvLanguageDisplay.text =
                    Locale.getDefault().getDisplayLanguage(Locale.getDefault())
            }
//            settingsGroup.switchNotification.setOnCheckedChangeListener { _, isChecked ->
//                // Turn on/off notification
//            }
//            val preferences = SettingPreferences.getInstance(requireContext().dataStore)
//            val preferencesViewModel =
//                ViewModelProvider(
//                    this@ProfileFragment,
//                    SettingViewModelFactory(preferences)
//                )[SettingViewModel::class.java]
//            preferencesViewModel.getThemeSetting()
//                .observe(viewLifecycleOwner) { isDarkMode: Boolean ->
//                    if (isDarkMode) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                        settingsGroup.switchDarkMode.isChecked = true
////                        preferencesViewModel.saveThemeSetting(true)
//                    } else {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                        settingsGroup.switchDarkMode.isChecked = false
////                        preferencesViewModel.saveThemeSetting(false)
//                    }
//                }
//            settingsGroup.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
//                preferencesViewModel.saveThemeSetting(isChecked)
//                updateNightMode(isChecked)
//            }
//            settingsGroup.btnHelpCenter.setOnClickListener {
//                // Go to Help Center
//            }
        }
    }
//    private fun updateNightMode(isDarkMode: Boolean) {
//        val nightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
//        AppCompatDelegate.setDefaultNightMode(nightMode)
//    }
}