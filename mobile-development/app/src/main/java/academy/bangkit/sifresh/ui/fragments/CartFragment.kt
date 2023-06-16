package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.data.MyCartDataDummy
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.FragmentCartBinding
import academy.bangkit.sifresh.ui.adapter.ListSellerCartAdapter
import academy.bangkit.sifresh.ui.viewmodels.CartViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        setUserData()

        viewModel.userId.observe(viewLifecycleOwner) {
            viewModel.getCartData(it)
        }

        binding.rvCartSeller.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.cartList.observe(viewLifecycleOwner) {
            binding.rvCartSeller.adapter = ListSellerCartAdapter(it)
        }
    }

    private fun setUserData() {
        val settingPreferences = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreferences)
        )[SettingViewModel::class.java]

        settingViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserID.name)
            .observe(viewLifecycleOwner) { id ->
                if (id != "") viewModel.userId.value = id
            }
    }
}