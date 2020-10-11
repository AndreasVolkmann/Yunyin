package me.avo.yunyin.domain

import me.avo.yunyin.entity.TrackEntity

data class Playlist(
    val id: Long? = null,
    val name: String = "",
    //tracks: List<Track> = listOf()
) {
    private var isLoaded = false

    val tracks: List<TrackEntity> get() {
        return listOf() // TODO
    }
}