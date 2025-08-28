package com.utkuaksu.demoapp.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkuaksu.demoapp.data.model.currency.Currency
import com.utkuaksu.demoapp.data.remote.currency.CurrencyApi
import com.utkuaksu.demoapp.databinding.FragmentHomeBinding
import com.utkuaksu.demoapp.data.repository.currency.CurrencyRepository
import com.utkuaksu.demoapp.ui.adapter.currency.CurrencyAdapter
import com.utkuaksu.demoapp.utils.Resource
import kotlin.collections.emptyList

class CurrencyFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory(CurrencyRepository(CurrencyApi.create(), requireContext()))
    }

    private val adapter = CurrencyAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.currencies.observe(viewLifecycleOwner) { resource ->
            when(resource){
                is Resource.Loading -> {/**/}
                is Resource.Success<*> -> adapter.updateList(resource.data ?: emptyList<Currency>())
                is Resource.Error<*> -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.fetchCurrencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}