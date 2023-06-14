package academy.bangkit.sifresh.data

import academy.bangkit.sifresh.data.response.ProductDummy
import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.data.response.Seller

object MyCartDataDummy {
    val myCartDummy = listOf(
        Seller(
            "Toko Buah Sukamaju",
            listOf(
                ProductCart(
                    ProductDummy(
                        ProductDataDummy.productDummyLists[0].productName, ProductDataDummy.productDummyLists[0].productPrice, ProductDataDummy.productDummyLists[0].productImageUrl
                    ),
                    1
                ),
                ProductCart(
                    ProductDummy(
                        ProductDataDummy.productDummyLists[2].productName, ProductDataDummy.productDummyLists[2].productPrice, ProductDataDummy.productDummyLists[2].productImageUrl
                    ),
                    3
                ),
                ProductCart(
                    ProductDummy(
                        ProductDataDummy.productDummyLists[5].productName, ProductDataDummy.productDummyLists[5].productPrice, ProductDataDummy.productDummyLists[5].productImageUrl
                    ),
                    1
                )
            ),
            34000.00
        ),
        Seller(
            "Toko Buah Sukamundur",
            listOf(
                ProductCart(
                    ProductDummy(
                        ProductDataDummy.productDummyLists[1].productName, ProductDataDummy.productDummyLists[1].productPrice, ProductDataDummy.productDummyLists[1].productImageUrl
                    ),
                    2
                ),
                ProductCart(
                    ProductDummy(
                        ProductDataDummy.productDummyLists[6].productName, ProductDataDummy.productDummyLists[6].productPrice, ProductDataDummy.productDummyLists[6].productImageUrl
                    ),
                    3
                )
            ),
            39000.00
        )
    )
}