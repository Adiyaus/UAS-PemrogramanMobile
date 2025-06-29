package com.example.meped.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import com.example.meped.data.local.dao.JadwalDao
import com.example.meped.data.local.dao.KlasemenDao
import com.example.meped.data.local.dao.TeamDao
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.data.local.entity.TeamEntity
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.dao.FavoriteDao
import com.example.meped.data.local.entity.FavoriteEntity

@Database(
    entities = [TeamEntity::class, KlasemenEntity::class, JadwalEntity::class, FavoriteEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun klasemenDao(): KlasemenDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meped-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}