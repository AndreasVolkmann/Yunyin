package me.avo.yunyin.view

import me.avo.yunyin.controller.ArtistBrowserController
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.*

class ArtistBrowserView : View("Artists") {

    private val controller: ArtistBrowserController by di()
    override val scope = super.scope as TrackFilterScope

    override fun onDock() {
        controller.refresh()
    }

    override val root = borderpane {
        left {
            tableview(controller.values) {
                //bindSelected(audioController.selectedTrack)
                //onDoubleClick(audioController::play)
                //readonlyColumn("#", Track::index).contentWidth(useAsMin = true, useAsMax = true)
                readonlyColumn("Name", Artist::name).remainingWidth()
                smartResize()
                onUserSelect(2) {
                    scope.model.item = it
                }
            }
        }

        right<TrackBrowserView>()
    }
}