package com.example.musicplayer.presentation.navigation

sealed class Screens(val route: String) {
    object HomeRoute : Screens("/")
    object SongRoute : Screens("/play-song/{music-uri}")
}

object ScreenConstants {
    const val PLAY_SONG_ROUTE_PARAM = "music-uri"
    const val SONG_ROUTE_URL = "/play-song/"
}
