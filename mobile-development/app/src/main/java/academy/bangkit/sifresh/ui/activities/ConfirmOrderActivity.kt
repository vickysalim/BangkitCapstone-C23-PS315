package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.MyCartDataDummy
import academy.bangkit.sifresh.data.ProductReviewDummy
import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.data.response.Review
import academy.bangkit.sifresh.databinding.ActivityConfirmOrderBinding
import academy.bangkit.sifresh.ui.adapter.ListItemCartAdapter
import academy.bangkit.sifresh.ui.adapter.ListProductReviewAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

class ConfirmOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCartItem.layoutManager = layoutManager

        setCartAdapter(MyCartDataDummy.myCartDummy[0].productCart)

        binding.btnConfirmPayment.setOnClickListener {
            val intent = Intent(this, OrderSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setCartAdapter(cartList: List<ProductCart>) {
        val adapter = ListItemCartAdapter(cartList)
        binding.rvCartItem.adapter = adapter
    }
}