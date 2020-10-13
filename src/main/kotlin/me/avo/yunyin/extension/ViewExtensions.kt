package me.avo.yunyin.extension

import javafx.event.EventTarget
import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.controller.PlaylistController
import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.view.fragment.PlaylistSelectFragment
import me.avo.yunyin.view.scope.TrackAddScope
import tornadofx.*

fun AudioController.openTrackAddModal() {
    if (selectedTrack.isNotNull.value) {
        val trackScope = TrackAddScope(selectedTrack.value)
        find<PlaylistSelectFragment>(trackScope).openModal()
    }
}

fun EventTarget.trackContextMenu(audioController: AudioController, playlistController: PlaylistController) {
    contextmenu {
        item("Add to Playlist") {
            enableWhen { audioController.selectedTrack.isNotNull }
            action(audioController::openTrackAddModal)
        }
        item("Remove from Playlist") {
            enableWhen {
                audioController.selectedTrack.isNotNull and
                        (audioController.scope.playQueue.item is PlayQueue.PlaylistPlayQueue)
            }
            action {
                val track = audioController.selectedTrack.get()
                playlistController.removeTrackFromCurrentPlaylist(track)
            }
        }
    }
}