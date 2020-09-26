package me.avo.yunyin.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Task
import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import me.avo.yunyin.model.FileItem
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tornadofx.Controller
import java.io.InputStream

@Component
class AudioController(

) : Controller() {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var player: AdvancedPlayer? = null
    private var pausedOnFrame = -1
    private var currentlyPlaying: FileItem? = null
    val currentInformation = SimpleStringProperty("-")
    val hasNext = SimpleBooleanProperty(false)
    val hasPrevious = SimpleBooleanProperty(false)

    fun playAudio(fileItem: FileItem) = runAsync {
        logger.info("playAudio - Start: $fileItem")
        currentlyPlaying = fileItem

        runAsync {
//            fileSystemController
//                .getStream(fileItem.driveItem)
//                .use(::play)
        }
    }  ui {
        currentInformation.value = "${fileItem.title} - ${fileItem.artist}"
//        hasNext.value = fileSystemController.hasNext()
//        hasPrevious.value = fileSystemController.hasPrevious()
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

    fun previous() {
        stop()
//        fileSystemController.previous()?.let(::playAudio)
    }

    fun next() {
        stop()
//        fileSystemController.next()?.let(::playAudio)
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
