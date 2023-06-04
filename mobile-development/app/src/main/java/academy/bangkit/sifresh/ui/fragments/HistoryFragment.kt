package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.HistoryDataDummy
import academy.bangkit.sifresh.databinding.FragmentHistoryBinding
import academy.bangkit.sifresh.ui.adapter.DetectHistoryAdapter
import academy.bangkit.sifresh.ui.adapter.OrderHistoryAdapter
import androidx.recyclerview.widget.LinearLayoutManager

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbTransaction.isChecked = true
        binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity())
        if (binding.rbTransaction.isChecked) {
            binding.rvHistory.adapter = OrderHistoryAdapter(HistoryDataDummy.orderHistory)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_transaction -> {
                    // History Order
                    binding.rvHistory.adapter =
                        OrderHistoryAdapter(HistoryDataDummy.orderHistory)
                }
                R.id.rb_detect -> {
                    // History Scan
                    binding.rvHistory.adapter =
                        DetectHistoryAdapter(HistoryDataDummy.detectHistory)
                }
            }
        }
    }
}