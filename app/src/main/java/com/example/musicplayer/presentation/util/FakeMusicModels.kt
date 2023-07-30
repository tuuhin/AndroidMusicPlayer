package com.example.musicplayer.presentation.util

import com.example.musicplayer.domain.models.MusicResourceModel

object FakeMusicModels {

    val fakeMusicResourceModel = MusicResourceModel(
        title = "Smooth Flute music ",
        displayName = "Flute Music",
        id = 100,
        artist = "Smooth Music Creators",
        album = "Instrumental",
        duration = 1000L,
        uri = "https://www.pagalworld.com.se/siteuploads/files/sfd133/66214/%20Fariyaad(PagalWorld.com.se).mp3",
        createdAt = 1200L
    )
    val fakeMusicResourceModelExtraLongNames = MusicResourceModel(
        title = "Smooth Flute music by Someone who hates to listen to this type of musics ",
        displayName = "Flute Music by Someone also known as the goat of the music",
        id = 100,
        artist = "Smooth Music Creators Obviously we cant create music",
        album = "Instrumental",
        duration = 1000L,
        uri = "https://www.pagalworld.com.se/siteuploads/files/sfd133/66214/%20Fariyaad(PagalWorld.com.se).mp3",
        createdAt = 1200L
    )
}