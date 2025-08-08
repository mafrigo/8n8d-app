package com.example.challengeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges")
    fun getAll(): Flow<List<Challenge>>

    @Insert
    suspend fun insert(challenge: Challenge)
    
    @Insert
    suspend fun insertAll(vararg challenges: Challenge)

    @Update
    suspend fun update(challenge: Challenge)
}