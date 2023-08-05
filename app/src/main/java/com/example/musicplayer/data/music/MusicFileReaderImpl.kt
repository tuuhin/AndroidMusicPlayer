package com.example.musicplayer.data.music

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.database.getStringOrNull
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.domain.music.MusicFileReader
import com.example.musicplayer.utils.NoReadPermissionsException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class MusicFileReaderImpl(
    private val context: Context
) : MusicFileReader {

    private val readAudioFilesPermission by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PermissionChecker.PERMISSION_GRANTED
        else ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    override suspend fun readMusicFiles(isAudioBooksToo: Boolean): List<MusicResourceModel> {
        return withContext(Dispatchers.IO) {

            val musicFiles = mutableListOf<MusicResourceModel>()

            try {
                if (!readAudioFilesPermission) throw NoReadPermissionsException()

                val volumeUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

                val projections = arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.SIZE,
                    MediaStore.Audio.Media.DATE_ADDED
                )

                val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"
                val selectionFileTypes =
                    "${MediaStore.Audio.Media.MIME_TYPE} IN ( ? , ? , ? )"
                val selectionFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    "${MediaStore.Audio.Media.IS_MUSIC}=1 OR ${MediaStore.Audio.Media.IS_AUDIOBOOK}=1"
                else "${MediaStore.Audio.Media.IS_MUSIC}=1"

                val finalSelections = "$selectionFile OR $selectionFileTypes"
                val finalSelectionArgs = arrayOf("audio/mpeg", "audio/wav", "audio/aac")

                context.contentResolver.query(
                    volumeUri,
                    projections,
                    finalSelections,
                    finalSelectionArgs,
                    sortOrder
                )
                    ?.use { cursor ->

                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                        val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                        val nameColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                        val artistColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                        val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                        val durationColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                        val dateAddedColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)

                        while (cursor.moveToNext()) {

                            val path = cursor.getString(dataColumn)

                            if (!File(path).exists()) continue

                            val id = cursor.getLong(idColumn)
                            val title = cursor.getString(titleColumn)
                            val displayName = cursor.getString(nameColumn)
                            val author = cursor.getStringOrNull(artistColumn)
                            val album = cursor.getStringOrNull(albumColumn)
                            val duration = cursor.getLong(durationColumn)
                            val uriString = ContentUris.withAppendedId(volumeUri, id).toString()

                            val dateAdded = cursor.getLong(dateAddedColumn)

                            val mediaMetadataReader = MediaMetadataRetriever()
                                .apply {
                                    setDataSource(
                                        context,
                                        ContentUris.withAppendedId(volumeUri, id)
                                    )
                                }

                            val albumArt = try {
                                mediaMetadataReader.embeddedPicture?.let { array ->
                                    BitmapFactory.decodeByteArray(array, 0, array.size)
                                }
                            } catch (e: Exception) {
                                null
                            } finally {
                                mediaMetadataReader.release()
                            }

                            musicFiles.add(
                                MusicResourceModel(
                                    title = title,
                                    displayName = displayName,
                                    id = id,
                                    artist = author,
                                    album = album,
                                    duration = duration,
                                    uri = uriString,
                                    albumArt = albumArt,
                                    createdAt = dateAdded
                                )
                            )
                        }
                    }
                musicFiles
            } catch (e: IOException) {
                e.printStackTrace()
                emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}