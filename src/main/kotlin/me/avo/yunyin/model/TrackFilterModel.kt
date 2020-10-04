package me.avo.yunyin.model

import me.avo.yunyin.entity.Artist
import tornadofx.ItemViewModel

class TrackFilterModel(
    artist: Artist?
) : ItemViewModel<Artist>(artist) {

    val artistId = bind(Artist::id)
    val name = bind(Artist::name)
}