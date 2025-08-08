package com.example.challengeapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.data.Challenge
import com.example.challengeapp.data.ChallengeDatabase
import com.example.challengeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ChallengeViewModel by viewModels {
        ChallengeViewModelFactory(
            ChallengeDatabase.getDatabase(
                this.applicationContext,
                viewModelScope
            )
        )
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ChallengeAdapter { challenge ->
            showEditDialog(challenge)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.challenges.observe(this) { challenges ->
            adapter.submitList(challenges)
        }
    }

    private fun showEditDialog(challenge: Challenge) {
        val editText = EditText(this).apply {
            setText(challenge.score.toString())
        }

        AlertDialog.Builder(this)
            .setTitle("Update Score")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newScore = editText.text.toString().toIntOrNull() ?: challenge.score
                val updatedChallenge = challenge.copy(score = newScore)
                viewModel.updateChallenge(updatedChallenge)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

class ChallengeViewModelFactory(
    private val database: ChallengeDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChallengeViewModel(database) as T
    }
}