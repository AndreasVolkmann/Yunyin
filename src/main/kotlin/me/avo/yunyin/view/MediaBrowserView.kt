package me.avo.yunyin.view

import me.avo.yunyin.controller.AudioController
import me.avo.yunyin.controller.MediaBrowserController
import me.avo.yunyin.entity.Track
import tornadofx.*

class MediaBrowserView : View("Browse") {
    private val controller: MediaBrowserController by di()
    private val audioController: AudioController by di()

    init {
        controller.init()
    }

    override val root = vbox {
        tableview(controller.values) {
            bindSelected(audioController.selectedTrack)
            onDoubleClick(audioController::play)

            //readonlyColumn("#", Track::index).contentWidth(useAsMin = true, useAsMax = true)
            readonlyColumn("Title", Track::title).remainingWidth()
            readonlyColumn("Artist", Track::artist)
            readonlyColumn("Album", Track::album)
            //readonlyColumn("Length", Track::length)
            smartResize()
        }

    }
}