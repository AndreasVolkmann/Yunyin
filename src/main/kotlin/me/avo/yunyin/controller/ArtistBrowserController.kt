package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.domain.Artist
import me.avo.yunyin.service.ArtistService
import me.avo.yunyin.service.PlayQueueService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
@Lazy
class ArtistBrowserController(
    private val artistService: ArtistService,
    private val playQueueService: PlayQueueService
) : Controller() {

    override val scope get() = super.scope as TrackFilterScope
    val values: ObservableList<Artist> = FXCollections.observableArrayList()

    fun refresh() {
        runAsync {
            artistService.findAll()
        } ui values::setAll
    }

    fun onSelect(artist: Artist?) {
        val playQueue = playQueueService.playQueueForArtist(artist)
        scope.playQueue.item = playQueue
    }
}