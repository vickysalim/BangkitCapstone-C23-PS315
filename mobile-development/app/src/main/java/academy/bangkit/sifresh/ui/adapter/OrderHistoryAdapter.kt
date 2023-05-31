package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.OrderHistory
import academy.bangkit.sifresh.databinding.OrderHistoryCardBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val listOrder: List<OrderHistory>) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderHistoryCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = listOrder[position]
        viewHolder.bind(order)
    }

    override fun getItemCount() = listOrder.size

    class ViewHolder (var binding: OrderHistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (order: OrderHistory) {
            with(binding) {
                tvOrderId.text = order.orderId
                tvOrderDate.text = order.orderDate
                tvOrderTotalPrice.text = order.orderTotalPrice
                tvOrderStatus.text = order.orderStatus
            }
        }
    }
}