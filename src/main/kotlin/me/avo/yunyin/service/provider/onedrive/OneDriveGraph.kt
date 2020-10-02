package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.models.extensions.DriveItem
import com.microsoft.graph.models.extensions.IGraphServiceClient
import com.microsoft.graph.requests.extensions.IDriveItemCollectionPage
import java.io.InputStream

class OneDriveGraph(
    authService: OneDriveAuthProvider
) {
    private val graphClient: IGraphServiceClient = Graph(authService.authToken).graphClient

    fun search(libraryFolderId: String): IDriveItemCollectionPage = tryGet {
        getFiles(libraryFolderId)
    }

    fun getStream(driveItem: DriveItem): InputStream = tryGet {
        graphClient
            .customRequest("/me/drive/items/${driveItem.id}/content", InputStream::class.java)
            .buildRequest()
            .get()
    }

    fun getStream(id: String): InputStream = tryGet {
        graphClient
            .customRequest("/me/drive/items/${id}/content", InputStream::class.java)
            .buildRequest()
            .get()
    }

    private fun getRoot(): IDriveItemCollectionPage = tryGet {
        graphClient
            .me().drive().root().children()
            .buildRequest()
            //.select(selectString)
            .get()
    }

    private fun getItem(id: String): DriveItem = tryGet {
        graphClient
            .me().drive().items(id)
            .buildRequest()
            .get()
    }

    fun getFiles(id: String): IDriveItemCollectionPage = tryGet {
        graphClient.me().drive()
            .items(id)
            .children()
            .buildRequest(listOf())
            //.select(selectString)
            .get()
    }

    private fun <T> tryGet(block: () -> T): T {
        var stack = 0
        while (stack < 3) {
            try {
                return block()
            } catch (ex: Exception) {
                println(ex)
                stack++
            }
        }

        throw IllegalStateException("Request failed retry")
    }
}