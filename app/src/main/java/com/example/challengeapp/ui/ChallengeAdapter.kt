package com.example.challengeapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.data.Challenge
import com.example.challengeapp.databinding.ListItemChallengeBinding

class ChallengeAdapter(
    private val onEditClick: (Challenge) -> Unit
) : ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ListItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ChallengeViewHolder(private val binding: ListItemChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: Challenge) {
            binding.apply {
                descriptionText.text = challenge.description
                scoreText.text = challenge.score.toString()
                editButton.setOnClickListener {
                    onEditClick(challenge)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge) =
                oldItem == newItem
        }
    }
}