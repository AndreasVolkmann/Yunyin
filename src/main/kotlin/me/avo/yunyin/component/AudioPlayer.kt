package me.avo.yunyin.component

import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.factory.StreamProviderFactory
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class AudioPlayer(
    private val streamProviderFactory: StreamProviderFactory,
    private val playbackListener: PlaybackListener
) {
    private var player: AdvancedPlayer? = null
    private var pausedOnFrame = -1

    fun play(track: Track) {
        streamProviderFactory
            .getStreamProvider(track.dataSourceType) // TODO generic
            .getStream(track)
            .use(::play)
    }

    private fun play(inputStream: InputStream) {
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

    fun handlePlaybackFinished(evt: PlaybackEvent) {
        println("playbackFinished ${evt.frame}")
        pausedOnFrame = evt.frame
    }
}