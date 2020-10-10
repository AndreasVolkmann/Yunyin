package me.avo.yunyin.view

import me.avo.yunyin.controller.AudioController
import tornadofx.*

class AudioPlayerControlView : View() {
    private val audioController: AudioController by di()

    override val root = hbox {
        label(audioController.currentInformation)

        button("Previous") {
            enableWhen(audioController::hasPrevious)
            action(audioController::previous)
        }
        button("Play") {
            action { }
        }
        button("Next") {
            enableWhen(audioController::hasNext)
            action(audioController::next)
        }
    }
}