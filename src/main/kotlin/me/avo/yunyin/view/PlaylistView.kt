package me.avo.yunyin.view

import me.avo.yunyin.controller.PlaylistController
import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.*

class PlaylistView : View("Playlists") {

    private val controller : PlaylistController by di()
    override val scope = super.scope as TrackFilterScope

    override fun onDock() {
        controller.refresh()
    }

    override val root = tableview(controller.playlists) {

        readonlyColumn("Playlist", Playlist::name).remainingWidth()
        smartResize()

        onSelectionChange {
            scope.selectionPlaylist.item = it
        }

        contextmenu {
            item("Create Playlist").action {
                controller.create()
            }
        }
    }
}