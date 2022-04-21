package com.sopian.challenge5.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sopian.challenge5.R
import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.databinding.FragmentMovieDetailBinding
import com.sopian.challenge5.utils.DateFormatter
import com.sopian.challenge5.utils.enableStatusBar
import com.sopian.challenge5.utils.loadPhotoUrl

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val binding: FragmentMovieDetailBinding by viewBinding()

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = (activity as AppCompatActivity).window

        var isToolbarShown = false

        if (!isToolbarShown) {
            enableStatusBar(false, view, window)
        }

        binding.apply {
            showDetails(args.movie)
            navigateUp.setOnClickListener {
                it.findNavController().navigateUp()
            }
            movieDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    val shouldShowToolbar = scrollY > 60
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar
                        binding.appbar.isActivated = shouldShowToolbar
                        binding.toolbarLayout.isTitleEnabled = shouldShowToolbar
                        binding.toolbarLayout.title = args.movie?.originalTitle
                    }

                    if (scrollY > 60) {
                        enableStatusBar(true, view, window)
                        binding.navigateUp.setImageResource(R.drawable.ic_detail_back_on_surface)
                    } else if (oldScrollY > scrollY) {
                        enableStatusBar(false, view, window)
                        binding.navigateUp.setImageResource(R.drawable.ic_detail_back_surface)
                    }
                }
            )
        }
    }

    private fun showDetails(data: MovieEntity?) {
        binding.apply {
            data?.let {
                detailImage.loadPhotoUrl(it.backdropPathUrl())
                titleTv.text = data.originalTitle
                voteTv.text = data.voteAverage
                releaseDateTv.text = DateFormatter.formatDate(data.releaseDate)
                overviewTv.text = data.overview
                posterImage.loadPhotoUrl(it.posterPathUrl())
            }
        }
    }
}