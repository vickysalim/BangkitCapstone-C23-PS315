package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.data.MyCartDataDummy
import academy.bangkit.sifresh.databinding.FragmentCartBinding
import academy.bangkit.sifresh.ui.adapter.ListSellerCartAdapter
import androidx.recyclerview.widget.LinearLayoutManager

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCartSeller.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCartSeller.adapter = ListSellerCartAdapter(MyCartDataDummy.myCartDummy)
    }
}