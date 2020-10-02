package me.avo.yunyin.service

import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.entity.Track
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.service.provider.StreamProviderFactory
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class AudioPlayerService(
    private val streamProviderFactory: StreamProviderFactory
) {
    private var player: AdvancedPlayer? = null
    private var pausedOnFrame = -1

    fun play(track: Track) {
        streamProviderFactory
            .getStreamProvider(DataSourceType.OneDrive) // TODO generic
            .getStream(track)
            .use(::play)
    }

    fun play(inputStream: InputStream) {
        stop()

        AdvancedPlayer(inputStream).let {
            player = it
            it.playBackListener = playbackListener
            it.play()
        }
    }

    fun stop() {
        player?.let {
            if (pausedOnFrame > 0) {
                it.stop()
            }
            else {
                it.close()
            }
        }
    }

    fun next() {
        stop()
//        fileSystemController.next()?.let(::playAudio)
    }

    fun previous() {
        stop()
//        fileSystemController.previous()?.let(::playAudio)
    }

    fun handlePlaybackFinished(evt: PlaybackEvent) {
        println("playbackFinished ${evt.frame}")
        pausedOnFrame = evt.frame
        next()
    }

    private val playbackListener: PlaybackListener = object : PlaybackListener() {
        override fun playbackFinished(evt: PlaybackEvent) {
            super.playbackFinished(evt)
            handlePlaybackFinished(evt)
        }
    }
}