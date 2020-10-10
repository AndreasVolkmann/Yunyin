package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.service.PlaylistService
import org.springframework.stereotype.Component
import tornadofx.Controller
import tornadofx.success

@Component
class PlaylistController(
    private val playlistService: PlaylistService
) : Controller() {

    val playlists: ObservableList<Playlist> = FXCollections.observableArrayList()

    fun refresh() {
        runAsync {
            playlistService.findAll()
        } ui {
            playlists.setAll(it)
        }
    }

    fun create() {
        runAsync {
            playlistService.createPlaylist()
        } success {
            refresh()
        }
    }

}
