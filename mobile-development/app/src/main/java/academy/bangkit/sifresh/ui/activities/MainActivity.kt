package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.databinding.ActivityMainBinding
import academy.bangkit.sifresh.ui.fragments.CartFragment
import academy.bangkit.sifresh.ui.fragments.HomeFragment
import academy.bangkit.sifresh.ui.fragments.HistoryFragment
import academy.bangkit.sifresh.ui.fragments.ProfileFragment
import academy.bangkit.sifresh.utils.Helper
import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchFragment(getCurrentFragment())

        binding.apply {
            bottomNavigationView.apply {
                menu.getItem(2).isEnabled = false

                setOnItemSelectedListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.nav_home -> {
                            switchFragment(HomeFragment())
                            true
                        }
                        R.id.nav_cart -> {
                            switchFragment(CartFragment())
                            true
                        }
                        R.id.nav_order -> {
                            switchFragment(HistoryFragment())
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

            fabScan.setOnClickListener {
                if (Helper.isPermissionGranted(this@MainActivity, Manifest.permission.CAMERA)) {
                    val intent = Intent(this@MainActivity, CameraActivity::class.java)
                    startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        REQUIRED_PERMISSION,
                        REQUEST_CODE_PERMISSION
                    )
                }
            }

        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }

    private fun getCurrentFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.content_frame) ?: HomeFragment()
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }
}