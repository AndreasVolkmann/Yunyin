package me.avo.yunyin.view.scope

import me.avo.yunyin.entity.Artist
import me.avo.yunyin.model.TrackFilterModel
import tornadofx.Scope

class TrackFilterScope(
    artist: Artist?
) : Scope() {

    val model = TrackFilterModel(artist)

}