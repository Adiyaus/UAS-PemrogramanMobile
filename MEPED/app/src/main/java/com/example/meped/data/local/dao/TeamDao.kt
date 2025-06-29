package com.example.meped.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meped.data.local.entity.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: TeamEntity)

    @Query("SELECT * FROM teams WHERE pageId = :id")
    fun getTeamById(id: Int): Flow<TeamEntity?>

    @Query("SELECT * FROM teams WHERE pageId = :id")
    suspend fun getTeamByIdOnce(id: Int): TeamEntity?

    @Query("UPDATE teams SET userNotes = :notes WHERE pageId = :id")
    suspend fun updateNotes(id: Int, notes: String)
}