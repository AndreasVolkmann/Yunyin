package me.avo.yunyin.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Task
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.entity.Track
import me.avo.yunyin.factory.AudioPlayerFactory
import me.avo.yunyin.service.ArtistService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
class AudioController(
    private val artistService: ArtistService,
    audioPlayerFactory: AudioPlayerFactory
) : Controller() {

    override val scope = super.scope as TrackFilterScope
    val selectedTrack = SimpleObjectProperty<Track>()
    val currentInformation = SimpleStringProperty("-")
    val hasNext = SimpleBooleanProperty(false)
    val hasPrevious = SimpleBooleanProperty(false)

    private val playbackListener: PlaybackListener = object : PlaybackListener() {
        override fun playbackFinished(evt: PlaybackEvent) {
            super.playbackFinished(evt)
            handlePlaybackFinished(evt)
        }
    }
    private val audioPlayer = audioPlayerFactory.makeAudioPlayer(playbackListener)
    private val playlist get() = scope.livePlaylist.item
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun play() {
        if (selectedTrack.isNotNull.value) {
            scope.livePlaylist.item = scope.selectionPlaylist.item
            playAudio(selectedTrack.get())
        }
    }

    fun next() {
        if (hasNext(selectedTrack.value)) {
            val nextIndex = playlist.tracks.indexOf(selectedTrack.value) + 1
            val nextTrack = playlist.tracks[nextIndex]
            playAudio(nextTrack)
        }
    }

    fun previous() {
        if (hasPrevious(selectedTrack.value)) {
            val nextIndex = playlist.tracks.indexOf(selectedTrack.value) - 1
            val nextTrack = playlist.tracks[nextIndex]
            playAudio(nextTrack)
        }
    }

    private fun playAudio(track: Track): Task<Artist?> {
        logger.info("playAudio - Start: $track")
        return runAsync {
            runAsync {
                audioPlayer.play(track, playlist)
            }
            track.artistId?.let(artistService::findArtistById)
        } ui { artist ->
            val artistName = artist?.name ?: ""
            currentInformation.value = "${track.title} - $artistName"
            hasNext.value = hasNext(track)
            hasPrevious.value = hasPrevious(track)
        }
    }

    private fun hasNext(track: Track): Boolean = playlist.tracks.indexOf(track) < playlist.tracks.size - 1

    private fun hasPrevious(track: Track): Boolean = playlist.tracks.indexOf(track) > 0

    private fun handlePlaybackFinished(evt: PlaybackEvent) {
        println("playbackFinished ${evt.frame}")
        audioPlayer.handlePlaybackFinished(evt)
        next()
    }
}
