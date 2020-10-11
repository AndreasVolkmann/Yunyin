package me.avo.yunyin.model

import me.avo.yunyin.entity.ArtistEntity
import tornadofx.ItemViewModel

class TrackFilterModel(
    artist: ArtistEntity?
) : ItemViewModel<ArtistEntity>(artist) {

    val artistId = bind(ArtistEntity::id)
    val name = bind(ArtistEntity::name)
}