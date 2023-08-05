package com.example.musicplayer.presentation.composables

import android.content.res.Configuration
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.presentation.util.rememberFormattedTimeFromLong
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun SongPlayerSlider(
    duration: Float,
    current: Float,
    ratio: Float,
    onSliderValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedTotalDuration = rememberFormattedTimeFromLong(time = duration)
    val formattedDuration = rememberFormattedTimeFromLong(time = current)

    val animatedSliderPosition by animateFloatAsState(
        targetValue = ratio,
        label = "SLIDER ANIMATION",
        animationSpec = tween(durationMillis = 200, easing = EaseIn)
    )

    Column(modifier = modifier) {
        Slider(
            value = animatedSliderPosition,
            onValueChange = onSliderValueChange,
            colors = SliderDefaults.colors(
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedDuration,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = formattedTotalDuration,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SongPlayerSliderPreview() {
    MusicPlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SongPlayerSlider(
                duration = 1800f,
                current = 1200f,
                ratio = 0.5f,
                onSliderValueChange = {},
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}