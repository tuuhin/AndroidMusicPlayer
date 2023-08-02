package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import com.example.musicplayer.presentation.composables.NoReadPermissions
import com.example.musicplayer.presentation.navigation.NavigationGraph
import com.example.musicplayer.presentation.util.checkAudioReadPermissions
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var listenableFuture: ListenableFuture<MediaController>

    override fun onDestroy() {
        // According to docs this should be released in the onStop lifecycle
        // But releasing the future ,we cannot get the media controller
        // as we are not initializing it on onStart but injecting it in ActivityRetainedScope
        // so releasing it here
        MediaController.releaseFuture(listenableFuture)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isReadPermissionProvided = checkAudioReadPermissions()

                    when {
                        isReadPermissionProvided -> NavigationGraph()

                        else -> NoReadPermissions()
                    }
                }
            }
        }
    }

}
