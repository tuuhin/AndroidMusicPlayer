package com.example.musicplayer.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun formattedTime(
    time: Long
): String {
    val localtime by remember(time) {
        derivedStateOf {
            Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("mm:ss"))
        }
    }
    return localtime
}