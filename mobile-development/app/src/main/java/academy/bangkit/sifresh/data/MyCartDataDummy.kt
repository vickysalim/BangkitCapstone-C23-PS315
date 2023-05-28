package academy.bangkit.sifresh.data

import academy.bangkit.sifresh.data.response.Product
import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.data.response.Seller

object MyCartDataDummy {
    val myCartDummy = listOf(
        Seller(
            "Toko Buah Sukamaju",
            listOf(
                ProductCart(
                    Product(
                        ProductDataDummy.productList[0].productName, ProductDataDummy.productList[0].productPrice, ProductDataDummy.productList[0].productImageUrl
                    ),
                    1
                ),
                ProductCart(
                    Product(
                        ProductDataDummy.productList[2].productName, ProductDataDummy.productList[2].productPrice, ProductDataDummy.productList[2].productImageUrl
                    ),
                    3
                ),
                ProductCart(
                    Product(
                        ProductDataDummy.productList[5].productName, ProductDataDummy.productList[5].productPrice, ProductDataDummy.productList[5].productImageUrl
                    ),
                    1
                )
            ),
            "Rp. 34.000"
        ),
        Seller(
            "Toko Buah Sukamundur",
            listOf(
                ProductCart(
                    Product(
                        ProductDataDummy.productList[1].productName,
                        ProductDataDummy.productList[1].productPrice,
                        ProductDataDummy.productList[1].productImageUrl
                    ),
                    2
                ),
                ProductCart(
                    Product(
                        ProductDataDummy.productList[6].productName,
                        ProductDataDummy.productList[6].productPrice,
                        ProductDataDummy.productList[6].productImageUrl
                    ),
                    3
                )
            ),
            "Rp. 39.000"
        )
    )
}