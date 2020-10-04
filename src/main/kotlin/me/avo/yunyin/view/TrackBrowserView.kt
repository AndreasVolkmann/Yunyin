package me.avo.yunyin.view

import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.controller.MediaBrowserController
import me.avo.yunyin.entity.Track
import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.*

class TrackBrowserView : View("Tracks") {
    private val controller: MediaBrowserController by di()
    private val audioController: AudioController by di()
    override val scope = super.scope as TrackFilterScope

    override val root = tableview(controller.values) {
        bindSelected(audioController.selectedTrack)
        onDoubleClick(audioController::play)

        //readonlyColumn("#", Track::index).contentWidth(useAsMin = true, useAsMax = true)
        readonlyColumn("Title", Track::title).remainingWidth()
        //readonlyColumn("Artist", Track::artist)
        readonlyColumn("Album", Track::album)
        //readonlyColumn("Length", Track::length)
        smartResize()
    }

    init {
        controller.init(scope.model)
    }

}