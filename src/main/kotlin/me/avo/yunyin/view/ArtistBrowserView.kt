package me.avo.yunyin.view

import me.avo.yunyin.controller.ArtistBrowserController
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.*

class ArtistBrowserView : View("Artists") {

    private val controller: ArtistBrowserController by di()
    override val scope = TrackFilterScope(null, Playlist(), Playlist())

    override fun onDock() {
        // TODO load previous session values
        controller.refresh()
    }

    override val root = borderpane {
        left {
            tableview(controller.values) {

                onSelectionChange {
                    scope.model.item = it
                }

                readonlyColumn("Artist", Artist::name).remainingWidth()
                smartResize()
            }
        }
        center<TrackBrowserView>()

        right<PlaylistView>()
    }
}