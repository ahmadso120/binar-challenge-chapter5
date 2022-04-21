package com.sopian.challenge5.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sopian.challenge5.databinding.FragmentHomeBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sopian.challenge5.utils.EventObserver
import com.sopian.challenge5.R
import com.sopian.challenge5.data.Result
import com.sopian.challenge5.storage.SharedPreferencesStorage
import com.sopian.challenge5.storage.Storage
import com.sopian.challenge5.ui.ViewModelFactory
import com.sopian.challenge5.ui.login.LoginFragment.Companion.EMAIL
import com.sopian.challenge5.utils.enableStatusBar
import com.sopian.challenge5.utils.showSnackBar

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()

    private val viewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[HomeViewModel::class.java]
    }

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var storage: Storage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storage = SharedPreferencesStorage(requireContext())

        val email = storage.getString(EMAIL)
        val welcomeMessage = "Hello, $email"
        setupToolbar(welcomeMessage)

        val window = (activity as AppCompatActivity).window

        enableStatusBar(true, view, window)

        setupAdapter()

        observeData(window)
    }

    private fun observeData(window: Window) {
        homeViewModel.menuItemSelectedLiveData.observe(viewLifecycleOwner, EventObserver {
            onMenuItemSelected(it)
        })

        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    Log.i("HomeFragment", "Loading")
                    showLoadingState()
                }
                is Result.Success -> {
                    Log.d("HomeFragment", it.data.toString())
                    showSuccessState()
                    movieAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    Log.i("HomeFragment", "Error")
                    showErrorState()
                    window.decorView.rootView.showSnackBar("Oops, something went wrong")
                }
            }
        }

        homeViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it)
            )
        })
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter {
            homeViewModel.onMovieClicked(it)
        }
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setupToolbar(welcomeMessage: String) {
        binding.apply {
            toolbar.title = welcomeMessage
            appbarLayout.setOnApplyWindowInsetsListener { v, windowInsets ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    v.updatePadding(top = windowInsets.getInsets(WindowInsets.Type.systemBars()).top)
                } else {
                    v.updatePadding(top = windowInsets.systemWindowInsetTop)
                }
                windowInsets
            }
            toolbar.setOnMenuItemClickListener {
                homeViewModel.onMenuItemSelected(it.itemId)
                true
            }
        }

    }

    private fun onMenuItemSelected(actionId: Int) {
        when (actionId) {
            R.id.user -> {
                findNavController().navigate(R.id.action_homeFragment_to_updateProfileFragment)
            }
        }
    }

    private fun showSuccessState() {
        binding.recyclerView.isVisible = true
        binding.contentLoadingLayout.hide()
    }

    private fun showErrorState() {
        binding.recyclerView.isVisible = true
        binding.contentLoadingLayout.hide()
    }

    private fun showLoadingState() {
        binding.recyclerView.isVisible = false
        binding.contentLoadingLayout.show()
    }

}