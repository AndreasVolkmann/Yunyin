package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.models.extensions.DriveItem
import com.microsoft.graph.requests.extensions.IDriveItemCollectionPage
import me.avo.yunyin.domain.DataSource
import me.avo.yunyin.entity.TrackStaging
import me.avo.yunyin.service.provider.DataProvider
import java.util.*

class OneDriveDataProvider(
    private val graph: OneDriveGraph,
    dataSource: DataSource
) : DataProvider {

    private val libraryId = dataSource.libraryId
    private val stack = Stack<DriveItem>()
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
            title = audio.title ?: driveItem.name
            artist = audio.artist ?: ""
            album = audio.album ?: ""
        }
    }

    private fun getTracksFromPage(page: IDriveItemCollectionPage): List<TrackStaging> {
        return page.currentPage
            .filter {
                if (it.folder != null && it.folder.childCount > 0) {
                    stack.add(it)
                }
                it.audio != null
            }
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
            val folder = stack.pop()
            return graph.getFiles(folder.id)
        }
    }

}