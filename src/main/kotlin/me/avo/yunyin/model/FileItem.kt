package me.avo.yunyin.model

import com.microsoft.graph.models.extensions.Audio
import com.microsoft.graph.models.extensions.DriveItem
import kotlin.reflect.KProperty1

class FileItem(val index: Int, val driveItem: DriveItem) {

    private val audio = driveItem.audio
    val isAudio = audio != null

    val title: String = getAudioData(Audio::title, driveItem.name)
    val artist: String = getAudioData(Audio::artist)
    val album: String = getAudioData(Audio::album)
    val length: String = if (isAudio && audio.duration != null) formatDuration(audio.duration) else ""

    override fun toString(): String = "FileItem(id:${driveItem.id}, $title - $artist)"

    private fun getAudioData(property: KProperty1<Audio, String?>, alternative: String = ""): String {
        return if (isAudio) property.get(audio) ?: alternative
        else alternative
    }

    private fun formatDuration(duration: Long): String {
        val inSeconds = duration / 1000
        val minutes = inSeconds / 60
        val seconds = inSeconds % 60
        return "${minutes}:${seconds}"
    }
}