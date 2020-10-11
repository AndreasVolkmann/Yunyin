package me.avo.yunyin.service.provider.onedrive

import me.avo.yunyin.domain.Track
import me.avo.yunyin.service.provider.StreamProvider
import java.io.InputStream

class OneDriveSteamProvider(
    private val graph: OneDriveGraph
) : StreamProvider {

    override fun getStream(track: Track): InputStream {
        return graph.getStream(track.itemId)
    }
}