package academy.bangkit.sifresh.data.response

data class OrderHistory(
    val orderId: String,
    val orderDate: String,
    val orderTotalPrice: String,
    val orderStatus: String
)
