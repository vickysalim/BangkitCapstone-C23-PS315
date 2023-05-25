package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.databinding.ActivityMainBinding
import academy.bangkit.sifresh.ui.fragments.CartFragment
import academy.bangkit.sifresh.ui.fragments.HomeFragment
import academy.bangkit.sifresh.ui.fragments.OrderFragment
import academy.bangkit.sifresh.ui.fragments.ProfileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchFragment(HomeFragment())

        binding.apply {
            bottomNavigationView.apply {
                menu.getItem(2).isEnabled = false

                setOnItemSelectedListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.nav_home -> {
                            switchFragment(HomeFragment())
                            true
                        }
                        R.id.nav_cart -> {
                            switchFragment(CartFragment())
                            true
                        }
                        R.id.nav_order -> {
                            switchFragment(OrderFragment())
                            true
                        }
                        R.id.nav_profile -> {
                            switchFragment(ProfileFragment())
                            true
                        }
                        else -> false
                    }
                }
            }

            fab.setOnClickListener {
                // Go to Camera Activity
            }

        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }
}