package academy.bangkit.sifresh.data

import academy.bangkit.sifresh.data.response.OrderHistory

object OrderHistoryDataDummy {
    val onProcess = listOf(
        OrderHistory(
            "M-1681289338-5147567",
            "24 Mei 2023 15:10:27",
            "Rp. 65.000",
            "Sedang Dikirm"
        ),
        OrderHistory(
            "M-1099379923-9876481",
            "26 Mei 2023 20:20:46",
            "Rp. 30.000",
            "Sedang Diproses"
        ),
        OrderHistory(
            "M-7432648162-1729478",
            "27 Mei 2023 08:32:14",
            "Rp. 42.000",
            "Belum Dibayar"
        )
    )

    val onComplete = listOf(
        OrderHistory(
            "M-9837235096-9491351",
            "20 Mei 2023 11:03:11",
            "Rp. 26.000",
            "Selesai"
        ),
        OrderHistory(
            "M-6306791732-8165396",
            "21 Mei 2023 18:19:21",
            "Rp. 76.000",
            "Selesai"
        )
    )
}