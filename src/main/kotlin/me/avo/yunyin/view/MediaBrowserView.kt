package me.avo.yunyin.view

import me.avo.yunyin.view.scope.TrackFilterScope
import tornadofx.View
import tornadofx.tabpane

class MediaBrowserView : View("Browse") {

    override val scope = TrackFilterScope(null)

    override val root = tabpane {
        tab<ArtistBrowserView> {
            setOnSelectionChanged {
                println("This!, $it")
            }
        }
        tab<TrackBrowserView> {
        }
    }
}