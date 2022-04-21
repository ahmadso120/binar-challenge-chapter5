package com.sopian.challenge5.ui.profile

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.sopian.challenge5.R
import com.sopian.challenge5.data.source.local.entity.UserEntity
import com.sopian.challenge5.databinding.FragmentProfileBinding
import com.sopian.challenge5.storage.SharedPreferencesStorage
import com.sopian.challenge5.storage.Storage
import com.sopian.challenge5.ui.ViewModelFactory
import com.sopian.challenge5.ui.login.LoginFragment.Companion.EMAIL
import com.sopian.challenge5.ui.login.LoginFragment.Companion.IS_LOGGED_IN
import com.sopian.challenge5.utils.showSnackBar
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class UpdateProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()

    private var currentSelectedDate: Long? = null

    private lateinit var storage: Storage

    private val viewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[ProfileViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storage = SharedPreferencesStorage(requireContext())

        binding.apply {
            appbarLayout.setOnApplyWindowInsetsListener { v, windowInsets ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    v.updatePadding(top = windowInsets.getInsets(WindowInsets.Type.systemBars()).top)
                } else {
                    v.updatePadding(top = windowInsets.systemWindowInsetTop)
                }
                windowInsets
            }

            toolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }

            dobEdt.setOnClickListener {
                showDatePicker()
            }

            val email = storage.getString(EMAIL)
            if (email != null) {
                profileViewModel.getUser(email).observe(viewLifecycleOwner) {
                    if (it != null) {
                        showProfileData(it)
                        updateButton.setOnClickListener { view ->
                            updateData(it, view)
                        }
                    }
                }
            }

            logoutButton.setOnClickListener {
                logoutProcess(it)
            }
        }
    }

    private fun logoutProcess(view: View) {
        storage.remove(IS_LOGGED_IN)
        storage.remove(EMAIL)
        profileViewModel.deleteAllMovie()
        profileViewModel.deleteAllUser()
        view.findNavController().navigate(R.id.action_updateProfileFragment_to_loginFragment)
        view.showSnackBar("Logout successfully")
    }

    private fun showProfileData(data: UserEntity) {
        binding.apply {
            usernameEdt.setText(data.username)
            dobEdt.setText(data.dob)
            fullnameEdt.setText(data.fullName)
            addressEdt.setText(data.address)
        }
    }

    private fun updateData(data: UserEntity, view: View) {
        binding.apply {
            val user = UserEntity(
                data.id,
                data.email,
                usernameEdt.text.toString().trim(),
                fullnameEdt.text.toString().trim(),
                dobEdt.text.toString().trim(),
                addressEdt.text.toString().trim(),
                data.password
            )

            profileViewModel.updateUser(user)

            view.showSnackBar("Data successfully updated")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val selectedDateInMillis = currentSelectedDate ?: System.currentTimeMillis()

        MaterialDatePicker.Builder.datePicker().setSelection(selectedDateInMillis).build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
        }.show(parentFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSelected(dateTimeStampInMillis: Long) {
        currentSelectedDate = dateTimeStampInMillis
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(
            currentSelectedDate!!
        ), ZoneId.systemDefault())
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))

        binding.dobEdt.setText(dateAsFormattedText)
    }

    override fun onDestroyView() {
        currentSelectedDate = null
        super.onDestroyView()
    }
}