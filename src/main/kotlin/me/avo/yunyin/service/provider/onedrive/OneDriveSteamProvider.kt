package me.avo.yunyin.service.provider.onedrive

import me.avo.yunyin.entity.Track
import me.avo.yunyin.service.provider.StreamProvider
import java.io.InputStream

class OneDriveSteamProvider(
    private val graph: OneDriveGraph
) : StreamProvider {

    override fun getStream(track: Track): InputStream {
        return track.itemId
            ?.let(graph::getStream)
            ?: throw IllegalStateException("Track has no itemId: $track")
    }
}