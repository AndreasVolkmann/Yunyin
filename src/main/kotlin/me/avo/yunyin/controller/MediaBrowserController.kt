package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.entity.Track
import me.avo.yunyin.repository.TrackRepository
import me.avo.yunyin.service.PlaylistService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tornadofx.Controller
import tornadofx.onChange

@Component
class MediaBrowserController(
    private val trackRepository: TrackRepository,
    private val playlistService: PlaylistService
) : Controller() {
    override val scope = super.scope as TrackFilterScope
    val values: ObservableList<Track> = FXCollections.observableArrayList()

    fun init() {
        refreshArtist(scope.model.item)
        scope.model.itemProperty.onChange(::refreshArtist)
        scope.selectionPlaylist.itemProperty.onChange(::refreshPlaylist)
    }

    @Transactional
    private fun refreshPlaylist(playlist: Playlist?) {
        if (playlist != null) {
            val tracks = playlistService.getTracks(playlist)
            values.setAll(tracks)
        }
    }

    private fun refreshArtist(artist: Artist?): Task<List<Track>?> = runAsync {
        artist?.id?.let(trackRepository::findAllByArtistId)
    } ui {
        if (it != null) {
            scope.selectionPlaylist.item = playlistService.createTemporaryPlaylist(it)
            values.setAll(it)
        }
    }
}