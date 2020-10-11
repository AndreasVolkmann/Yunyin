package me.avo.yunyin.model

import me.avo.yunyin.domain.PlayQueue
import tornadofx.ItemViewModel

class PlaylistModel(
    playQueue: PlayQueue
) : ItemViewModel<PlayQueue>(playQueue)