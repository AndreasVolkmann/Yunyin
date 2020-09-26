package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.models.extensions.DriveItem
import com.microsoft.graph.requests.extensions.IDriveItemCollectionPage
import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.entity.TrackStaging
import me.avo.yunyin.service.provider.DataProvider
import java.util.*

class OneDriveDataProvider(
    private val graph: OneDriveGraph,
    private val dataSource: DataSource
) : DataProvider {

    private val libraryId = dataSource.libraryId
    private val stack = Stack<IDriveItemCollectionPage>()
    private var isFirstCall = true

    override fun hasNext(): Boolean {
        return isFirstCall || stack.isNotEmpty()
    }

    override fun next(): Collection<TrackStaging> {
        val page = getCurrentPage()
        return if (page != null) {
            getTracksFromPage(page)
        }
        else {
            listOf()
        }
    }

    private fun toTrackStaging(driveItem: DriveItem): TrackStaging {
        val audio = driveItem.audio
        return TrackStaging().apply {
            itemId = driveItem.id
            title = audio.title
            artist = audio.artist
            album = audio.album
        }
    }

    private fun getTracksFromPage(page: IDriveItemCollectionPage): List<TrackStaging> {
        return page.currentPage
            .filter { it.audio != null }
            .map(::toTrackStaging)
    }

    private fun getCurrentPage(): IDriveItemCollectionPage? {
        if (isFirstCall) {
            if (libraryId != null) {
                val page = graph.getFiles(libraryId)
                isFirstCall = false
                return page
            }

            return null
        }
        else {
            return TODO()
        }
    }

}