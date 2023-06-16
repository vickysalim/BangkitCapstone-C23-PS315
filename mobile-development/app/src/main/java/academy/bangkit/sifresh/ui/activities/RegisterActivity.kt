package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.databinding.ActivityRegisterBinding
import academy.bangkit.sifresh.ui.fragments.RegisterAddressFragment
import academy.bangkit.sifresh.ui.fragments.RegisterInfoFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val registerInfoFragment = RegisterInfoFragment()
        val fragment =
            fragmentManager.findFragmentByTag(RegisterInfoFragment::class.java.simpleName)

        fragmentManager.addOnBackStackChangedListener {
            val currentFragment = fragmentManager.findFragmentById(R.id.frame_container)
            currentFragment?.let {
                updateStepper(it)
            }
        }

        if (fragment !is RegisterInfoFragment) {
            fragmentManager.beginTransaction()
                .add(
                    R.id.frame_container,
                    registerInfoFragment,
                    RegisterInfoFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun updateStepper(fragment: Fragment) {
        val density = resources.displayMetrics.density
        val activeDpToPx = (56 * density + 0.5f).toInt()
        val inactiveDpToPx = (48 * density + 0.5f).toInt()

        val stepper1 = binding.stepper1.layoutParams
        val stepper2 = binding.stepper2.layoutParams

        when (fragment) {
            is RegisterInfoFragment -> {
                binding.stepper1.background = getDrawable(R.drawable.stepper_active_container)
                binding.stepper2.background = getDrawable(R.drawable.stepper_inactive_container)

                stepper1.width = activeDpToPx
                stepper1.height = activeDpToPx

                stepper2.width = inactiveDpToPx
                stepper2.height = inactiveDpToPx
            }
            is RegisterAddressFragment -> {
                binding.stepper1.background = getDrawable(R.drawable.stepper_inactive_container)
                binding.stepper2.background = getDrawable(R.drawable.stepper_active_container)

                stepper1.width = inactiveDpToPx
                stepper1.height = inactiveDpToPx

                stepper2.width = activeDpToPx
                stepper2.height = activeDpToPx
            }
        }

        binding.stepper1.layoutParams = stepper1
        binding.stepper2.layoutParams = stepper2
    }
}