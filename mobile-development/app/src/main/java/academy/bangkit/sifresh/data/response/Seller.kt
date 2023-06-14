package academy.bangkit.sifresh.data.response

data class Seller(
    val sellerName: String,
    val productCart: List<ProductCart>,
    val totalPrice: Double
)
