package me.avo.yunyin.view.scope

import me.avo.yunyin.entity.Artist
import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.model.PlaylistModel
import me.avo.yunyin.model.TrackFilterModel
import tornadofx.Scope

class TrackFilterScope(
    artist: Artist?,
    livePlaylist: Playlist,
    selectionPlaylist: Playlist
) : Scope() {

    val model = TrackFilterModel(artist)
    val livePlaylist = PlaylistModel(livePlaylist)
    val selectionPlaylist = PlaylistModel(selectionPlaylist)
}