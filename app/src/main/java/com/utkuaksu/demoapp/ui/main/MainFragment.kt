package com.utkuaksu.demoapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.utkuaksu.demoapp.MainViewModel
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.databinding.FragmentMainBinding
import com.utkuaksu.demoapp.ui.adapter.viewpager.ViewPagerAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { ViewPagerAdapter(requireActivity()) }
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // GoogleSignInClient oluştur
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Eğer kullanıcı zaten giriş yaptıysa ViewModel’e set et
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        viewModel.setUserFromGoogle(account)

        // Hamburger menü
        binding.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }

        // NavigationView header
        val headerView = binding.navigationView.getHeaderView(0)
        val imgProfile = headerView.findViewById<ImageView>(R.id.imgProfile)
        val txtUserName = headerView.findViewById<TextView>(R.id.txtUserName)

        // User LiveData observer
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                txtUserName.text = it.displayName ?: "Kullanıcı"
                Glide.with(this)
                    .load(it.photoUrl)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .into(imgProfile)
            } ?: run {
                txtUserName.text = "Kullanıcı"
                imgProfile.setImageResource(R.drawable.ic_person_placeholder)
            }
        }

        // Çıkış yap ve LoginFragment’e dön
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.action_logout) {
                googleSignInClient.signOut().addOnCompleteListener {
                    viewModel.logout()
                    findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                }
                true
            } else false
        }

        // ViewPager + TabLayout
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Currency"
                1 -> "Emtia"
                2 -> "Share"
                else -> ""
            }
        }.attach()

        // SearchView ikon renkleri
        val searchIcon: ImageView = binding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter("#F8F9FA".toColorInt())

        val closeIcon: ImageView = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeIcon.setColorFilter("#F8F9FA".toColorInt())

        val searchEditText: TextView = binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor("#F8F9FA".toColorInt())

        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
