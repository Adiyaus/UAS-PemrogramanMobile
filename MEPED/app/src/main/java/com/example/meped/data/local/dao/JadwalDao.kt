package com.example.meped.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meped.data.local.entity.JadwalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<JadwalEntity>)

    @Query("SELECT * FROM schedules WHERE turnamen = :namaTurnamen")
    fun getSchedulesByTournament(namaTurnamen: String): Flow<List<JadwalEntity>>
}