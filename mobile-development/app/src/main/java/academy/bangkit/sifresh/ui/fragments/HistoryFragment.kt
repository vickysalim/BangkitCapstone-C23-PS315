package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.HistoryDataDummy
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.databinding.FragmentHistoryBinding
import academy.bangkit.sifresh.ui.adapter.DetectHistoryAdapter
import academy.bangkit.sifresh.ui.adapter.OrderHistoryAdapter
import academy.bangkit.sifresh.ui.viewmodels.HistoryViewModel
import academy.bangkit.sifresh.ui.viewmodels.RegisterViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class.java]

        setUserData()

        viewModel.userId.observe(viewLifecycleOwner) {
            viewModel.getOrderList(it)
        }

        binding.rbTransaction.isChecked = true
        binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity())
        viewModel.orderList.observe(viewLifecycleOwner) {
            binding.rvHistory.adapter = OrderHistoryAdapter(it)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_transaction -> {
                    // History Order
                    viewModel.orderList.observe(viewLifecycleOwner) {
                        binding.rvHistory.adapter = OrderHistoryAdapter(it)
                    }
                }
                R.id.rb_detect -> {
                    // History Scan
//                    viewModel.detectList.observe(viewLifecycleOwner) {
//                        binding.rvHistory.adapter = DetectHistoryAdapter(it)
//                    }
                }
            }
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