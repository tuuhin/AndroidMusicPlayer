package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.presentation.AudioFilesRoute
import com.example.musicplayer.presentation.AudioFilesViewModel
import com.example.musicplayer.presentation.util.checkAudioReadPermissions
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val readPermission = checkAudioReadPermissions()
                    if (readPermission) {
                        val viewModel = hiltViewModel<AudioFilesViewModel>()
                        val files by viewModel.audioFiles.collectAsStateWithLifecycle()
                        AudioFilesRoute(music = files)
                    }
                }
            }
        }
    }
}
