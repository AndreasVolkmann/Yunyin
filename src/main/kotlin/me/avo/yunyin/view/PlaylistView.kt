package me.avo.yunyin.view

import me.avo.yunyin.controller.PlaylistController
import me.avo.yunyin.domain.Playlist
import tornadofx.*

class PlaylistView : View("Playlists") {

    private val controller: PlaylistController by di()

    override fun onDock() {
        controller.refresh()
    }

    override val root = tableview(controller.playlists) {
        readonlyColumn("Playlist", Playlist::name).remainingWidth()
        smartResize()

        onSelectionChange(controller::onSelect)

        contextmenu {
            item("Create Playlist").action {
                controller.create()
            }
        }
    }
}