package com.example.meped.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meped.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favorites: List<FavoriteEntity>)

    @Query("SELECT * FROM favorites WHERE itemType = :type")
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorites WHERE itemId =:itemId")
    suspend fun deleteFavoriteById(itemId: Int)
}