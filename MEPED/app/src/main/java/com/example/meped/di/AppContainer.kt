package com.example.meped.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.meped.data.local.OnboardingManager
import com.example.meped.data.local.database.AppDatabase
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.data.remote.api.LiquipediaApiService
import com.example.meped.data.repository.impl.TeamRepositoryImpl
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {


    val teamRepository: TeamRepository
    val onboardingManager = OnboardingManager(context)


    init {
        Log.d("MEPED_FINAL_DEBUG", "AppContainer di-init. Memulai setup...")

        val databaseScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        val appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "meped-db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.d("MEPED_FINAL_DEBUG", "DATABASE DIBUAT! Callback onCreate terpanggil.")
                    // Kita akan panggil fungsi untuk mengisi data dari scope
                    databaseScope.launch {
                        prepopulateDatabase(AppDatabase.getInstance(context.applicationContext))
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()

        // --- BAGIAN API SERVICE ---
        val apiService = Retrofit.Builder()
            .baseUrl("https://liquipedia.net/mobilelegends/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LiquipediaApiService::class.java)

        teamRepository = TeamRepositoryImpl(
            apiService = apiService,
            teamDao = appDatabase.teamDao(),
            klasemenDao = appDatabase.klasemenDao(),
            jadwalDao = appDatabase.jadwalDao(),
            favoriteDao = appDatabase.favoriteDao()
        )
        Log.d("MEPED_FINAL_DEBUG", "AppContainer selesai dibuat. Semua dependencies siap.")
    }

    private suspend fun prepopulateDatabase(database: AppDatabase) {
        try {
            Log.d("MEPED_FINAL_DEBUG", "Memasukkan data klasemen...")
            val dummyStandings = listOf(
                KlasemenEntity(rank = 1, teamName = "ONIC", wins = 12, losses = 4, points = 25, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 2, teamName = "EVOS Glory", wins = 11, losses = 5, points = 21, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 3, teamName = "Geek Fam ID", wins = 10, losses = 6, points = 20, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 4, teamName = "Bigetron Alpha", wins = 9, losses = 7, points = 18, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 5, teamName = "AURA Fire", wins = 8, losses = 8, points = 15, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 6, teamName = "RRQ Hoshi", wins = 7, losses = 9, points = 12, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 7, teamName = "Alter Ego", wins = 6, losses = 10, points = 10, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 8, teamName = "Rebellion Esports", wins = 5, losses = 11, points = 8, turnamen = "MPL ID S13"),
                KlasemenEntity(rank = 9, teamName = "Dewa United", wins = 4, losses = 12, points = 5, turnamen = "MPL ID S13")
            )
            database.klasemenDao().insertAll(dummyStandings)
            Log.d("MEPED_FINAL_DEBUG", "BERHASIL memasukkan ${dummyStandings.size} data klasemen.")

            Log.d("MEPED_FINAL_DEBUG", "Memasukkan data jadwal...")
            val dummySchedules = listOf(
                JadwalEntity(week = "WEEK 1", date = "JUMAT, 10 MEI", time = "15:00 WIB", teamA_name = "Alter Ego", teamB_name = "EVOS Glory", status = "2-0", turnamen = "MPL ID S13"),
                JadwalEntity(week = "WEEK 1", date = "JUMAT, 10 MEI", time = "17:30 WIB", teamA_name = "AURA Fire", teamB_name = "ONIC", status = "1-2", turnamen = "MPL ID S13"),
                JadwalEntity(week = "WEEK 2", date = "JUMAT, 17 MEI", time = "15:00 WIB", teamA_name = "RRQ Hoshi", teamB_name = "Geek Fam", status = "VS", turnamen = "MPL ID S13"),
                JadwalEntity(week = "WEEK 2", date = "JUMAT, 17 MEI", time = "17:30 WIB", teamA_name = "Bigetron Alpha", teamB_name = "Dewa United", status = "VS", turnamen = "MPL ID S13")
            )
            database.jadwalDao().insertAll(dummySchedules)
            Log.d("MEPED_FINAL_DEBUG", "BERHASIL memasukkan ${dummySchedules.size} data jadwal.")
        } catch (e: Exception) {
            Log.e("MEPED_FINAL_DEBUG", "ERROR di dalam prepopulateDatabase: ", e)
        }
    }
}