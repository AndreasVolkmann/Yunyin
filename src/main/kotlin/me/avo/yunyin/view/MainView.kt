package me.avo.yunyin.view

import javafx.geometry.Side
import javafx.scene.control.TabPane
import me.avo.yunyin.controller.MainController
import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.*

class MainView : View("Yunyin - Cloud sound") {

    override val scope = TrackFilterScope(null)
    private val controller: MainController by di()

    override fun onUndock() {
        super.onUndock()
        controller.stop()
    }

    override val root = borderpane {
        setPrefSize(800.0, 600.0)
        controller.load()

        center = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            side = Side.LEFT
            tab<ArtistBrowserView>()
            tab<SettingsView>()
        }


        bottom<AudioPlayerControlView>()
    }
}


