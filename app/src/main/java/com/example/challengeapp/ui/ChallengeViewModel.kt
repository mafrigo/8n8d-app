package com.example.challengeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeapp.data.Challenge
import com.example.challengeapp.data.ChallengeDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChallengeViewModel(private val database: ChallengeDatabase) : ViewModel() {
    val challenges = database.challengeDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            database.challengeDao().update(challenge)
        }
    }
}