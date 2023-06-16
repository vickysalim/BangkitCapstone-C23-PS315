package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.OrderHistoryData
import academy.bangkit.sifresh.databinding.OrderHistoryCardBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val listOrder: List<OrderHistoryData>) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderHistoryCardBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = listOrder[position]
        viewHolder.bind(order)
    }

    override fun getItemCount() = listOrder.size

    class ViewHolder(var binding: OrderHistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderHistoryData) {
            with(binding) {
                tvOrderId.text = order.id
                tvOrderDate.text = order.orderDate
                tvOrderStatus.text = order.status
            }
        }
    }
}