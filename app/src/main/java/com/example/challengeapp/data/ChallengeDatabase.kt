package com.example.challengeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Challenge::class],
    version = 1,
    exportSchema = false
)
abstract class ChallengeDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao

    companion object {
        @Volatile
        private var INSTANCE: ChallengeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ChallengeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChallengeDatabase::class.java,
                    "challenge_database"
                )
                    .addCallback(ChallengeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ChallengeDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        database.challengeDao().insertAll(
                            Challenge(name = "5km Run", description = "Complete 5km run", score = 0),
                            Challenge(name = "Daily Reading", description = "Read 30 minutes daily", score = 0)
                        )
                    }
                }
            }
        }
                Challenge(description = "Meditate for 10 minutes", score = 0)
            )
            challenges.forEach { dao.insert(it) }
        }
    }
}