package me.avo.yunyin.view

import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.controller.MediaBrowserController
import me.avo.yunyin.domain.Track
import me.avo.yunyin.extension.trackContextMenu
import tornadofx.*

class TrackBrowserView : View("Tracks") {
    private val controller: MediaBrowserController by di()
    private val audioController: AudioController by di()

    override val root = tableview(controller.values) {
        bindSelected(audioController.selectedTrack)
        onDoubleClick(audioController::play)

        //readonlyColumn("#", Track::index).contentWidth(useAsMin = true, useAsMax = true)
        readonlyColumn("Title", Track::title).remainingWidth()
        readonlyColumn("Album", Track::album)
        //readonlyColumn("Length", Track::length)
        smartResize()

        trackContextMenu(audioController)
    }

    init {
        controller.init()
    }

}