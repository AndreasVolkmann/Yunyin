package me.avo.yunyin.view

import me.avo.yunyin.controller.MainController
import me.avo.yunyin.model.FileItem
import tornadofx.*

class BrowserView : View() {

    private val controller: MainController by di()

    override val root = vbox {
        hbox {
            button("Back") {
                enableWhen(controller::canGoBack)
                action(controller::back)
            }
            button("Set library") {
                action(controller::setLibraryFolder)
            }
            label(controller.currentFolderName)

        }
        tableview(controller.values) {
            bindSelected(controller.selectedItem)
            onDoubleClick(controller::selectItem)
            readonlyColumn("#", FileItem::index).contentWidth(useAsMin = true, useAsMax = true)
            readonlyColumn("Title", FileItem::title).remainingWidth()
            readonlyColumn("Artist", FileItem::artist)
            readonlyColumn("Album", FileItem::album)
            readonlyColumn("Length", FileItem::length)
            smartResize()
        }

        add(AudioPlayerControlView())
    }
}