package com.example.musicplayer.domain.models


data class MusicResourceModel(
    val uri: String? = null,
    val title: String,
    val displayName: String,
    val id: Long,
    val artist: String? = null,
    val album: String? = null,
    val duration: Long,
    val albumArt: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MusicResourceModel

        if (uri != other.uri) return false
        if (title != other.title) return false
        if (displayName != other.displayName) return false
        if (id != other.id) return false
        if (artist != other.artist) return false
        if (album != other.album) return false
        if (duration != other.duration) return false
        if (albumArt != null) {
            if (other.albumArt == null) return false
            if (!albumArt.contentEquals(other.albumArt)) return false
        } else if (other.albumArt != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (album?.hashCode() ?: 0)
        result = 31 * result + duration.hashCode()
        result = 31 * result + (albumArt?.contentHashCode() ?: 0)
        return result
    }
}
