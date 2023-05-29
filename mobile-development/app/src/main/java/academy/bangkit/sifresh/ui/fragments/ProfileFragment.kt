package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.databinding.FragmentProfileBinding
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
            }
            btnLogOut.setOnClickListener {
                // Log out (Back to Login Activity)
            }
            settingsGroup.tvLanguageDisplay.text =
                Locale.getDefault().getDisplayLanguage(Locale.getDefault())
            settingsGroup.btnChangeLanguage.setOnClickListener {
                // Change Language
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                settingsGroup.tvLanguageDisplay.text =
                    Locale.getDefault().getDisplayLanguage(Locale.getDefault())
            }
            settingsGroup.switchNotification.setOnCheckedChangeListener { _, isChecked ->
                // Turn on/off notification
            }
            val preferences = SettingPreferences.getInstance(requireContext().dataStore)
            val preferencesViewModel =
                ViewModelProvider(
                    this@ProfileFragment,
                    SettingViewModelFactory(preferences)
                )[SettingViewModel::class.java]
            preferencesViewModel.getThemeSetting()
                .observe(viewLifecycleOwner) { isDarkMode: Boolean ->
                    if (isDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        settingsGroup.switchDarkMode.isChecked = true
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        settingsGroup.switchDarkMode.isChecked = false
                    }
                }
            settingsGroup.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                preferencesViewModel.saveThemeSetting(isChecked)
            }
            settingsGroup.btnHelpCenter.setOnClickListener {
                // Go to Help Center
            }
        }
    }
}