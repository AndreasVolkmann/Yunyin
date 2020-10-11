package me.avo.yunyin.view

import me.avo.yunyin.controller.ArtistBrowserController
import me.avo.yunyin.domain.Artist
import tornadofx.*

class ArtistBrowserView : View("Artists") {

    private val controller: ArtistBrowserController by di()

    override fun onDock() {
        // TODO load previous session values
        controller.refresh()
    }

    override val root = borderpane {
        left {
            tableview(controller.values) {
                onSelectionChange(controller::onSelect)

                readonlyColumn("Artist", Artist::name).remainingWidth()
                smartResize()
            }
        }

        center<TrackBrowserView>()

        right<PlaylistView>()
    }
}