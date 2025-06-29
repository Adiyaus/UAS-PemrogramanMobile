package com.example.meped.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meped.data.local.entity.KlasemenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KlasemenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(standings: List<KlasemenEntity>)

    @Query("SELECT * FROM standings WHERE turnamen = :namaTurnamen ORDER BY rank ASC")
    fun getStandingsByTournament(namaTurnamen: String): Flow<List<KlasemenEntity>>
}