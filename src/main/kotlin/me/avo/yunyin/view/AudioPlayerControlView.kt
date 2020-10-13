package me.avo.yunyin.view

import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.controller.PlaylistController
import me.avo.yunyin.extension.trackContextMenu
import tornadofx.*

class AudioPlayerControlView : View() {
    private val audioController: AudioController by di()
    private val playlistController: PlaylistController by di()

    override val root = hbox {
        label(audioController.currentInformation) {

            trackContextMenu(audioController, playlistController)
        }

        button("Previous") {
            enableWhen(audioController::hasPrevious)
            action(audioController::previous)
        }
        button(audioController.playStatus) {
            enableWhen { audioController.selectedTrack.isNotNull }
            action(audioController::playPause)
        }
        button("Next") {
            enableWhen(audioController::hasNext)
            action(audioController::next)
        }

    }
}