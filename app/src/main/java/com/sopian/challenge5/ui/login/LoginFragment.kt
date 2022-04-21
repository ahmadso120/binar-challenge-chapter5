package com.sopian.challenge5.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sopian.challenge5.R
import com.sopian.challenge5.databinding.FragmentLoginBinding
import com.sopian.challenge5.storage.SharedPreferencesStorage
import com.sopian.challenge5.storage.Storage
import com.sopian.challenge5.ui.ViewModelFactory
import com.sopian.challenge5.ui.register.RegisterViewModel
import com.sopian.challenge5.utils.showSnackBar

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()

    private val args: LoginFragmentArgs by navArgs()

    private val viewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[LoginViewModel::class.java]
    }

    private lateinit var storage: Storage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storage = SharedPreferencesStorage(requireContext())

        binding.apply {
            emailEdt.setText(args.email)
            signupTv.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }

            loginButton.setOnClickListener { view ->
                val email = emailEdt.text.toString().trim()
                val password = passwordEdt.text.toString().trim()
                doLogin(email, password, view)
            }
        }

    }

    private fun doLogin(email: String, password: String, view: View) {
        loginViewModel.doLogin(email, password).observe(viewLifecycleOwner) {
            if (it != null) {
                loginViewModel.setIsAuthorized(it, true)
                storage.setBoolean(IS_LOGGED_IN, true)
                storage.setString(EMAIL, email)
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(it)
                )
            } else {
                view.showSnackBar("Login failed!")
            }
        }
    }

    companion object {
        const val IS_LOGGED_IN = "is_logged_in"
        const val EMAIL = "email"
    }
}