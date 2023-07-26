package com.example.musicplayer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.domain.music.MusicFileReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioFilesViewModel @Inject constructor(
    private val reader: MusicFileReader,
) : ViewModel() {

    private val _files = MutableStateFlow<List<MusicResourceModel>>(emptyList())
    val audioFiles = _files.asStateFlow()

    init {
        loadFiles()
    }

    fun loadFiles() {
        viewModelScope.launch {
            val files = reader.readMusicFiles()
            Log.d("FILES", files.toString())
            _files.update { files }
        }
    }
}