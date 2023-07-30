package com.example.musicplayer.presentation.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun formatTimeFromLong(time: Long): String {
    val localtime by remember(time) {
        derivedStateOf {
            val dateTime = Instant.ofEpochMilli(time)
                .atZone(ZoneId.of("Europe/London"))
                .toLocalTime()
            val formatter = DateTimeFormatter.ofPattern("mm:ss")
            dateTime.format(formatter)
        }
    }
    return localtime
}

@Preview
@Composable
fun TimeFormatPreview() {
    val time = formatTimeFromLong(time = 1000L)
    Text(text = time)
}