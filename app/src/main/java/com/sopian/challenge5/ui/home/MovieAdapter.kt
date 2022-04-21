package com.sopian.challenge5.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.databinding.ItemMovieBinding
import com.sopian.challenge5.utils.loadPhotoUrl

class MovieAdapter(
    private var onDetailClick: (MovieEntity) -> Unit
) : ListAdapter<MovieEntity, MovieAdapter.CarViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {

            photoImageView.loadPhotoUrl(item.posterPathUrl())
            titleTextView.text = item.originalTitle
            root.setOnClickListener {
                onDetailClick(item)
            }
        }
    }

    class CarViewHolder(
        val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
        oldItem == newItem
}