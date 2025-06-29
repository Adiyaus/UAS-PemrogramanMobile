package com.example.meped.data.remote.api

import com.example.meped.data.remote.dto.LiquipediaResponseDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface LiquipediaApiService {

    /**
     * Fungsi umum untuk mengambil anggota dari sebuah kategori di Liquipedia.
     * Ini akan d pakai untuk mengambil daftar Tim, Player, dan Turnamen.
     */
    @GET("api.php")
    suspend fun getCategoryMembers(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("list") list: String = "categorymembers",
        @Query("cmtitle") category: String, // Contoh: "Category:Teams" atau "Category:Players"
        @Query("cmlimit") limit: Int = 50 // Maksimal 50 item per panggilan
    ): LiquipediaResponseDto

    /**
     * Fungsi untuk mengambil konten HTML dari sebuah halaman.
     * Ini d pakai untuk mengambil deskripsi tim di halaman Detail.
     */
    @GET("api.php")
    suspend fun getPageContent(
        @Query("action") action: String = "parse",
        @Query("page") pageTitle: String,
        @Query("prop") prop: String = "text",
        @Query("format") format: String = "json"
    ): ResponseBody
}