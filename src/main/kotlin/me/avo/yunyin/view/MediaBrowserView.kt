package me.avo.yunyin.view

import me.avo.yunyin.controller.MediaBrowserController
import me.avo.yunyin.entity.Track
import me.avo.yunyin.model.FileItem
import tornadofx.*

class MediaBrowserView : View("Browse") {
    private val controller: MediaBrowserController by di()

    init {
        controller.init()
    }

    override val root = vbox {
        tableview(controller.values) {
            //bindSelected(controller.selectedItem)
            //onDoubleClick(controller::selectItem)
            //readonlyColumn("#", Track::index).contentWidth(useAsMin = true, useAsMax = true)
            readonlyColumn("Title", Track::title).remainingWidth()
            readonlyColumn("Artist", Track::artist)
            readonlyColumn("Album", Track::album)
            //readonlyColumn("Length", Track::length)
            smartResize()
        }

    }
}