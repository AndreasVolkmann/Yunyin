package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.service.PlayQueueService
import me.avo.yunyin.service.PlaylistService
import me.avo.yunyin.view.scope.TrackFilterScope
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import tornadofx.Controller
import tornadofx.success

@Component
@Lazy
class PlaylistController(
    private val playlistService: PlaylistService,
    private val playQueueService: PlayQueueService
) : Controller() {
    override val scope get() = super.scope as TrackFilterScope
    val playlists: ObservableList<Playlist> = FXCollections.observableArrayList()

    fun refresh() {
        runAsync {
            playlistService.findAll()
        } ui playlists::setAll
    }

    fun create() {
        runAsync {
            playlistService.createPlaylist()
        } success {
            refresh()
        }
    }

    fun onSelect(playlist: Playlist?) {
        val playQueue = playQueueService.playQueueForPlaylist(playlist)
        scope.playQueue.item = playQueue
    }

    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        runAsync {
            playlistService.addTrackToPlaylist(track, playlist)
        }
    }

    fun removeTrackFromCurrentPlaylist(track: Track) {
        val playQueue = scope.playQueue.item
        if (playQueue is PlayQueue.PlaylistPlayQueue) {
            val playlist = playQueue.playlist
            runAsync {
                playlistService.removeTrackFromPlaylist(track, playlist)
            }
        }
    }
}
