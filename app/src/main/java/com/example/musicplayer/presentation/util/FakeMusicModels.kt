package com.example.musicplayer.presentation.util

import com.example.musicplayer.domain.models.MusicResourceModel

object FakeMusicModels {

    val fakeMusicResourceModel = MusicResourceModel(
        title = "Smooth Flute music ",
        displayName = "Flute Music",
        id = 100,
        artist = "Smooth Music Creators",
        album = "Instrumental",
        duration = 1000L
    )
}