package me.avo.yunyin.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Task
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.domain.Artist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.enum.PlayStatus
import me.avo.yunyin.factory.AudioPlayerFactory
import me.avo.yunyin.service.ArtistService
import me.avo.yunyin.service.PlayQueueService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
@Lazy
class AudioController(
    private val artistService: ArtistService,
    private val playQueueService: PlayQueueService,
    audioPlayerFactory: AudioPlayerFactory
) : Controller() {

    private val playbackListener: PlaybackListener = object : PlaybackListener() {
        override fun playbackFinished(evt: PlaybackEvent) {
            super.playbackFinished(evt)
            handlePlaybackFinished(evt)
        }
    }
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val audioPlayer = audioPlayerFactory.makeAudioPlayer(playbackListener)
    private var tracks: List<Track> = listOf()
    private var currentIndex: Int = -1
    private var currentPlayStatus = PlayStatus.Play

    override val scope get() = super.scope as TrackFilterScope
    val selectedTrack = SimpleObjectProperty<Track>()
    val currentInformation = SimpleStringProperty("-")
    val hasNext = SimpleBooleanProperty(false)
    val hasPrevious = SimpleBooleanProperty(false)
    val isPlaying = SimpleBooleanProperty(false)
    val playStatus = SimpleStringProperty(currentPlayStatus.name)

    fun play() {
        if (selectedTrack.isNotNull.value) {
            val playQueue = scope.playQueue.item
            tracks = playQueueService.getTracks(playQueue)
            val track = selectedTrack.get()
            playAudio(track)
        }
    }

    fun playPause() {
        when (currentPlayStatus) {
            PlayStatus.Pause -> audioPlayer.stop()
            PlayStatus.Play -> runAsync {
                audioPlayer.resume()
            }
        }
    }

    fun next() {
        if (hasNext()) {
            val nextIndex = currentIndex + 1
            val nextTrack = tracks[nextIndex]
            playAudio(nextTrack)
        }
    }

    fun previous() {
        if (hasPrevious()) {
            val nextIndex = currentIndex - 1
            val nextTrack = tracks[nextIndex]
            playAudio(nextTrack)
        }
    }

    private fun playAudio(track: Track): Task<Artist?> {
        logger.info("playAudio - Start: $track")
        currentIndex = track.index
        return runAsync {
            startPlayThread(track)
            artistService.findArtistById(track.artistId)
        } ui { artist ->
            val artistName = artist?.name ?: ""
            currentInformation.value = "${track.title} - $artistName"
            hasNext.value = hasNext()
            hasPrevious.value = hasPrevious()
            setPlayStatus(PlayStatus.Pause)
        }
    }

    private fun startPlayThread(track: Track) {
        runAsync {
            audioPlayer.play(track)
        } ui {
            setPlayStatus(PlayStatus.Play)
        }
    }

    private fun hasNext(): Boolean = currentIndex < tracks.size - 1

    private fun hasPrevious(): Boolean = currentIndex > 0

    private fun handlePlaybackFinished(evt: PlaybackEvent) {
        println("playbackFinished ${evt.frame}")
        audioPlayer.handlePlaybackFinished(evt)
        next()
    }

    private fun setPlayStatus(playStatus: PlayStatus) {
        currentPlayStatus = playStatus
        isPlaying.value = playStatus == PlayStatus.Play
        this.playStatus.value = playStatus.name
    }
}
