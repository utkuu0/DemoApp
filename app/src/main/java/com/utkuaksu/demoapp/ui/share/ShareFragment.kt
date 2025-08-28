package com.utkuaksu.demoapp.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkuaksu.demoapp.data.model.share.Share
import com.utkuaksu.demoapp.data.remote.share.ShareApi
import com.utkuaksu.demoapp.databinding.FragmentHomeBinding
import com.utkuaksu.demoapp.data.repository.share.ShareRepository
import com.utkuaksu.demoapp.ui.adapter.share.ShareAdapter
import com.utkuaksu.demoapp.utils.Resource
import kotlin.collections.emptyList

class ShareFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShareViewModel by viewModels {
        ShareViewModelFactory(ShareRepository(ShareApi.create(), requireContext()))
    }

    private val adapter = ShareAdapter(emptyList())

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

        viewModel.shares.observe(viewLifecycleOwner) { resource ->
            when(resource){
                is Resource.Loading -> {/**/}
                is Resource.Success<*> -> adapter.updateList(resource.data ?: emptyList<Share>())
                is Resource.Error<*> -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.fetchShares()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}