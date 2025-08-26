package com.utkuaksu.demoapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.adapter.CurrencyAdapter
import com.utkuaksu.demoapp.api.CurrencyApi
import com.utkuaksu.demoapp.databinding.FragmentHomeBinding
import com.utkuaksu.demoapp.model.Currency
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CurrencyAdapter
    private val currencyList = mutableListOf<Currency>()
    private lateinit var api: CurrencyApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CurrencyAdapter(currencyList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // burada sınıf seviyesindeki api initialize ediliyor
        val apiKey = getString(R.string.collect_api_key)
        api = CurrencyApi.create(apiKey)

        fetchCurrencies()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchCurrencies() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = api.getCurrencies()
                Log.d("Currencies", response.toString())

                currencyList.clear()
                currencyList.addAll(response.result)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Log.e("API_ERROR", "Hata: ${e.message}", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
