package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Track
import me.avo.yunyin.service.PlayQueueService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.springframework.stereotype.Component
import tornadofx.Controller
import tornadofx.onChange

@Component
class MediaBrowserController(
    private val playQueueService: PlayQueueService
) : Controller() {

    override val scope = super.scope as TrackFilterScope
    val values: ObservableList<Track> = FXCollections.observableArrayList()

    fun init() {
        refreshPlaylist(scope.playQueue.item)
        scope.playQueue.itemProperty.onChange(::refreshPlaylist)
    }

    private fun refreshPlaylist(playQueue: PlayQueue?) {
        val tracks = playQueueService.getTracks(playQueue ?: PlayQueue.EmptyPlayQueue)
        values.setAll(tracks)
    }
}