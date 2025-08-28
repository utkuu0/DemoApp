package com.utkuaksu.demoapp.ui.emtia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkuaksu.demoapp.data.remote.emtia.EmtiaApi
import com.utkuaksu.demoapp.databinding.FragmentHomeBinding
import com.utkuaksu.demoapp.data.repository.emtia.EmtiaRepository
import com.utkuaksu.demoapp.ui.adapter.emtia.EmtiaAdapter
import com.utkuaksu.demoapp.utils.Resource
import kotlin.collections.emptyList

class EmtiaFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmtiaViewModel by viewModels {
        EmtiaViewModelFactory(EmtiaRepository(EmtiaApi.create(), requireContext()))
    }

    private val adapter = EmtiaAdapter(emptyList())

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

        viewModel.emtias.observe(viewLifecycleOwner) { resource ->
            when(resource){
                is Resource.Loading -> {/**/}
                is Resource.Success<*> -> adapter.updateList(resource.data ?: emptyList())
                is Resource.Error<*> -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.fetchEmtias()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}