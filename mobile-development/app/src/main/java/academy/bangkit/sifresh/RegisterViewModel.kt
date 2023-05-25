package academy.bangkit.sifresh

import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null

    var province: String? = null
    var city: String? = null
    var district: String? = null
    var postalCode: String? = null
    var address: String? = null
}