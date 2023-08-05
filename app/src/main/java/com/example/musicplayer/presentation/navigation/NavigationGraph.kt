package com.example.musicplayer.presentation.navigation

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.presentation.routes.AudioFilesRoute
import com.example.musicplayer.presentation.routes.AudioFilesViewModel
import com.example.musicplayer.presentation.routes.PlaySongRoute
import com.example.musicplayer.presentation.routes.SharedSongPlayerViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier
) {
    val navHost = rememberNavController()

    val sharedViewModel = hiltViewModel<SharedSongPlayerViewModel>()

    val currentSelectedSong by sharedViewModel.currentSelectedSong.collectAsStateWithLifecycle()

    NavHost(
        navController = navHost,
        startDestination = Screens.HomeRoute.route,
        modifier = modifier
    ) {
        composable(route = Screens.HomeRoute.route) {

            val viewModel = hiltViewModel<AudioFilesViewModel>()

            val files by viewModel.audioFiles.collectAsStateWithLifecycle()
            val sortState by viewModel.sortState.collectAsStateWithLifecycle()

            AudioFilesRoute(
                currentSong = currentSelectedSong,
                music = files,
                sortState = sortState,
                onSortEvents = viewModel::onSortEvents,
                onItemSelect = sharedViewModel::onSongSelect,
                onSongEvents = sharedViewModel::onPlaySongEvents,
                onDetailsRoute = { uri ->
                    val encodedUri = Uri.encode(uri)
                    navHost.navigate(ScreenConstants.SONG_ROUTE_URL + encodedUri)
                }
            )
        }
        composable(
            route = Screens.SongRoute.route,
            arguments = listOf(
                navArgument(ScreenConstants.PLAY_SONG_ROUTE_PARAM) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val trackData by sharedViewModel.trackDataFlow.collectAsStateWithLifecycle()

            PlaySongRoute(
                onNavigation = {
                    IconButton(
                        onClick = {
                            if (navHost.previousBackStackEntry != null)
                                navHost.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                },
                songState = currentSelectedSong,
                onSongEvents = sharedViewModel::onPlaySongEvents,
                trackData = trackData
            )
        }
    }
}
