package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityDetectResultBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide

class DetectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            Glide.with(imgResult)
                .load("https://gust-production.s3.amazonaws.com/uploads/startup/panoramic_image/1007246/fruits-and-Vegetables-banner.jpg")
                .into(imgResult)

            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}