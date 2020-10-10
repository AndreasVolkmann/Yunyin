package me.avo.yunyin.factory

import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.component.AudioPlayer
import org.springframework.stereotype.Service

@Service
class AudioPlayerFactory(
    private val streamProviderFactory: StreamProviderFactory
) {

    fun makeAudioPlayer(playbackListener: PlaybackListener) =
        AudioPlayer(streamProviderFactory, playbackListener)

}