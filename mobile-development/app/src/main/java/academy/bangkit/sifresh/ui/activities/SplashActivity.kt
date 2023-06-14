package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivitySplashBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(
            Looper.getMainLooper()
        ).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DURATION_MILLIS)
    }

    companion object {
        private const val SPLASH_DURATION_MILLIS: Long = 2500
    }
}