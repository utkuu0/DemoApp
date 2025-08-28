package com.utkuaksu.demoapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.utkuaksu.demoapp.databinding.FragmentMainBinding
import com.utkuaksu.demoapp.ui.adapter.viewpager.ViewPagerAdapter

class MainFragment: Fragment(){

        private var _binding: FragmentMainBinding? = null
        private val binding get() = _binding!!

        private val adapter by lazy { ViewPagerAdapter(requireActivity()) } // FragmentStateAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentMainBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.viewPager.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Currency"
                    1 -> tab.text = "Emtia"
                    2 -> tab.text = "Share"
                }
            }.attach()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}