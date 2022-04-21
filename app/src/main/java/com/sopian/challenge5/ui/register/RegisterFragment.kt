package com.sopian.challenge5.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.sopian.challenge5.R
import com.sopian.challenge5.data.source.local.entity.UserEntity
import com.sopian.challenge5.databinding.FragmentRegisterBinding
import com.sopian.challenge5.ui.ViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()

    private val viewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[RegisterViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            signupButton.setOnClickListener {
                val username = binding.usernameEdt.text.toString().trim()
                val password = binding.passwordEdt.text.toString().trim()
                val confirmPassword = binding.confirmPasswordEdt.text.toString().trim()
                val email = binding.emailEdt.text.toString().trim()

                if (password != confirmPassword) {
                    Snackbar.make(it, "The confirm password doesn't match", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val user = UserEntity(
                    id = 0,
                    email = email,
                    username = username,
                    password = password
                )

                registerViewModel.registerUser(user)

                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(email)
                )
            }
        }

    }
}