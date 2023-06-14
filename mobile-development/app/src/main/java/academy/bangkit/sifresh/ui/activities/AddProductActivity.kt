package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityAddProductBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}