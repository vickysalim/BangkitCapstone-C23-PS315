package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityOrderSuccessBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener {
            finish()
        }
    }
}