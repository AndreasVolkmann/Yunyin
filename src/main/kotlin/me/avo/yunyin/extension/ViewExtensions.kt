package me.avo.yunyin.extension

import javafx.event.EventTarget
import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.view.fragment.PlaylistSelectFragment
import me.avo.yunyin.view.scope.TrackAddScope
import tornadofx.*

fun AudioController.openTrackAddModal() {
    if (selectedTrack.isNotNull.value) {
        val trackScope = TrackAddScope(selectedTrack.value)
        find<PlaylistSelectFragment>(trackScope).openModal()
    }
}

fun EventTarget.trackContextMenu(audioController: AudioController) {
    contextmenu {
        item("Add to Playlist") {
            enableWhen { audioController.selectedTrack.isNotNull }
            action(audioController::openTrackAddModal)
        }
    }
}