package com.example.meped.data

import com.example.meped.data.remote.dto.TeamDto

object DummyDataSource {

    val dummyTeams = listOf(
        TeamDto(pageId = 101, title = "ONIC"),
        TeamDto(pageId = 102, title = "EVOS Glory"),
        TeamDto(pageId = 103, title = "RRQ Hoshi"),
        TeamDto(pageId = 104, title = "Bigetron Alpha"),
        TeamDto(pageId = 105, title = "Alter Ego"),
        TeamDto(pageId = 106, title = "AURA Fire"),
        TeamDto(pageId = 107, title = "Geek Fam ID"),
        TeamDto(pageId = 108, title = "Rebellion Esports"),
        TeamDto(pageId = 109, title = "Dewa United Esports")
    )

    val dummyPlayers = listOf(
        TeamDto(pageId = 301, title = "Kairi"),
        TeamDto(pageId = 302, title = "Sanz"),
        TeamDto(pageId = 303, title = "Butss"),
        TeamDto(pageId = 304, title = "CW"),
        TeamDto(pageId = 305, title = "Kiboy"),
        TeamDto(pageId = 306, title = "Skylar"),
        TeamDto(pageId = 307, title = "Clayyy"),
        TeamDto(pageId = 308, title = "Alberttt"),
        TeamDto(pageId = 309, title = "Vyn")
    )

    val dummyTournaments = listOf(
        TeamDto(pageId = 501, title = "MPL Indonesia Season 13"),
        TeamDto(pageId = 502, title = "MPL Philippines Season 13"),
        TeamDto(pageId = 503, title = "MSC 2024"),
        TeamDto(pageId = 504, title = "M6 World Championship")
    )
}