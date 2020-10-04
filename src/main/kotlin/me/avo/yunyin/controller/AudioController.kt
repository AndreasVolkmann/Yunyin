package me.avo.yunyin.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Task
import me.avo.yunyin.entity.Track
import me.avo.yunyin.service.AudioPlayerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
class AudioController(
    private val audioPlayerService: AudioPlayerService
) : Controller() {

    val selectedTrack = SimpleObjectProperty<Track>()
    private val logger = LoggerFactory.getLogger(this::class.java)

    val currentInformation = SimpleStringProperty("-")
    val hasNext = SimpleBooleanProperty(false)
    val hasPrevious = SimpleBooleanProperty(false)

    fun play() {
        if (selectedTrack.isNotNull.value) {
            playAudio(selectedTrack.get())
        }
    }

    private fun playAudio(track: Track): Task<Task<Unit>> {
        logger.info("playAudio - Start: $track")
        return runAsync {
            runAsync {
                audioPlayerService.play(track)
            }
        } ui {
            currentInformation.value = "${track.title} - ${track.artistId}"
            //        hasNext.value = fileSystemController.hasNext()
            //        hasPrevious.value = fileSystemController.hasPrevious()
        }
    }

}
