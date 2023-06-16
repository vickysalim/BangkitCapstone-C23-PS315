package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityOrderSuccessBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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