package me.avo.yunyin.view.fragment

import javafx.beans.property.SimpleObjectProperty
import me.avo.yunyin.controller.PlaylistController
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.view.scope.TrackAddScope
import tornadofx.Fragment
import tornadofx.bindSelected
import tornadofx.listview
import tornadofx.onChange

class PlaylistSelectFragment : Fragment() {

    override val scope = super.scope as TrackAddScope
    private val playlistController: PlaylistController by di()
    private val selectedPlaylist = SimpleObjectProperty<Playlist>().apply {
        onChange {
            it?.let { playlist ->
                playlistController.addTrackToPlaylist(scope.track, playlist)
                close()
            }
        }
    }

    override val root = listview(playlistController.playlists) {
        bindSelected(selectedPlaylist)
        cellFormat {
            text = it.name
        }
    }
}