package com.utkuaksu.demoapp.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.databinding.FragmentLoginBinding
import androidx.core.graphics.toColorInt

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001

    private val prefs by lazy {
        requireActivity().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Eğer autoLogin açıksa ve kullanıcı giriş yapmışsa direkt Main'e git
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val autoLogin = prefs.getBoolean("autoLogin", false)
        if (autoLogin && account != null) {
            navigateToHome(account.displayName ?: "Kullanıcı")
            return
        }

        // GoogleSignInOptions ID Token ile
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnGoogleSignIn.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

        // Switch (Beni Hatırla)
        binding.switchRememberMe.isChecked = prefs.getBoolean("autoLogin", false)
        binding.switchRememberMe.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("autoLogin", isChecked).apply()
        }

        // Loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // ViewModel gözlemle
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                navigateToHome(it.displayName ?: "Kullanıcı")
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Log.e("LoginFragment", it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { viewModel.firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                val message = getFriendlyGoogleSignInError(e.statusCode)
                view?.let {
                    Snackbar.make(it, message, Snackbar.LENGTH_LONG).apply {
                        setBackgroundTint("#E0E0E0".toColorInt())
                        setTextColor("#0D1B2A".toColorInt())
                        show()
                    }
                }
            }
        }
    }

    // Helper function for friendly error messages
    fun getFriendlyGoogleSignInError(code: Int): String {
        return when(code) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Sign-in was cancelled."
            GoogleSignInStatusCodes.SIGN_IN_FAILED -> "Google sign-in failed."
            GoogleSignInStatusCodes.NETWORK_ERROR -> "Network error. Please check your connection."
            GoogleSignInStatusCodes.INTERNAL_ERROR -> "Server error. Please try again later."
            else -> "An unknown error occurred. Code: $code"
        }
    }



    private fun navigateToHome(userName: String) {
        val bundle = Bundle().apply { putString("userName", userName) }
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

