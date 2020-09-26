package me.avo.yunyin.view

import javafx.geometry.Side
import me.avo.yunyin.controller.MainController
import tornadofx.View
import tornadofx.borderpane
import tornadofx.tabpane

class MainView : View("Yunyin - Cloud sound") {

    private val controller: MainController by di()

    override fun onUndock() {
        super.onUndock()
        controller.stop()
    }

    override val root = borderpane {
        setPrefSize(640.0, 480.0)
        controller.load()

        center = tabpane {
            side = Side.LEFT
            tab<MediaBrowserView>()
            tab<SettingsView>()
        }
    }
}


