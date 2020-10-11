package me.avo.yunyin.domain

sealed class PlayQueue {

    object EmptyPlayQueue : PlayQueue()

    class PlaylistPlayQueue(
        val playlist: Playlist
    ) : PlayQueue()

    class ArtistPlayQueue(
        val artist: Artist
    ) : PlayQueue()
}