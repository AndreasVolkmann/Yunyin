package me.avo.yunyin.view.scope

import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.entity.ArtistEntity
import me.avo.yunyin.model.PlaylistModel
import me.avo.yunyin.model.TrackFilterModel
import tornadofx.Scope

class TrackFilterScope(
    artist: ArtistEntity?,
//    livePlaylist: PlayQueue = PlayQueue.EmptyPlayQueue,
    playQueue: PlayQueue = PlayQueue.EmptyPlayQueue
) : Scope() {

    val model = TrackFilterModel(artist)

    //    val livePlaylist = PlaylistModel(livePlaylist)
    val playQueue = PlaylistModel(playQueue)
}