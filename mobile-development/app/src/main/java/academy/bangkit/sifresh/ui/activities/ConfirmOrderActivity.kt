package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.MyCartDataDummy
import academy.bangkit.sifresh.data.ProductReviewDummy
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.data.response.*
import academy.bangkit.sifresh.databinding.ActivityConfirmOrderBinding
import academy.bangkit.sifresh.ui.adapter.ListItemCartAdapter
import academy.bangkit.sifresh.ui.adapter.ListItemOrderAdapter
import academy.bangkit.sifresh.ui.adapter.ListProductReviewAdapter
import academy.bangkit.sifresh.ui.adapter.ListSellerCartAdapter
import academy.bangkit.sifresh.ui.viewmodels.OrderViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import academy.bangkit.sifresh.utils.Helper
import academy.bangkit.sifresh.utils.ResponseCode
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class ConfirmOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmOrderBinding

    private lateinit var viewModel: OrderViewModel

    private lateinit var sellerId: String
    private lateinit var sellerName: String
    private lateinit var totalPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        sellerId = intent.getStringExtra(ListSellerCartAdapter.SELLER) ?: ""
        sellerName = intent.getStringExtra(ListSellerCartAdapter.SELLER_NAME) ?: ""
        totalPrice = intent.getStringExtra(ListSellerCartAdapter.TOTAL_PRICE) ?: ""

        setUserData()

        viewModel.userId.observe(this) {
            viewModel.getOrderList(it, sellerId)
        }

        binding.apply {
            tvSellerName.text = sellerName
            tvPrice.text = Helper.formatCurrency(totalPrice.toDouble())
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvCartItem.layoutManager = layoutManager

        viewModel.orderList.observe(this) {
            setCartAdapter(it)
        }

        binding.btnConfirmPayment.setOnClickListener {
            viewModel.updateOrderStatus(viewModel.userId.value.toString(), sellerId, "complete")

            viewModel.updateOrderStatus.observe(this) {
                if(it == ResponseCode.SUCCESS)
                    viewModel.insertOrderHistory(viewModel.userId.value.toString())
            }

            viewModel.insertStatus.observe(this) {
                if(it == ResponseCode.SUCCESS) {
                    val intent = Intent(this, OrderSuccessActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setCartAdapter(orderList: List<OrderListItem>) {
        val adapter = ListItemOrderAdapter(orderList)
        binding.rvCartItem.adapter = adapter
    }

    private fun setUserData() {
        val settingPreferences = SettingPreferences.getInstance(this.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreferences)
        )[SettingViewModel::class.java]

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserID.name)
            .observe(this) { id ->
                if (id != "") viewModel.userId.value = id
            }

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserToken.name)
            .observe(this) { token ->
                if (token != "") viewModel.userToken.value = token
            }
    }
}