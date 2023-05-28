package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.OrderHistoryDataDummy
import academy.bangkit.sifresh.databinding.FragmentOrderBinding
import academy.bangkit.sifresh.ui.adapter.OrderHistoryAdapter
import androidx.recyclerview.widget.LinearLayoutManager

class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbOnProcess.isChecked = true
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(requireActivity())
        if (binding.rbOnProcess.isChecked) {
            binding.rvOrderHistory.adapter = OrderHistoryAdapter(OrderHistoryDataDummy.onProcess)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_onProcess -> {
                    // On Process Order
                    binding.rvOrderHistory.adapter =
                        OrderHistoryAdapter(OrderHistoryDataDummy.onProcess)
                }
                R.id.rb_onComplete -> {
                    // On Complete Order
                    binding.rvOrderHistory.adapter =
                        OrderHistoryAdapter(OrderHistoryDataDummy.onComplete)
                }
            }
        }
    }
}