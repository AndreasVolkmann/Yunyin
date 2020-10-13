package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.models.extensions.DriveItem
import com.microsoft.graph.requests.extensions.IDriveItemCollectionPage
import org.springframework.retry.annotation.Retryable
import java.io.InputStream

class OneDriveGraph(
    authService: OneDriveAuthProvider
) {
    private val graphClient = Graph(authService.authToken).graphClient

    @Retryable
    fun getStream(id: String): InputStream = graphClient
        .customRequest("/me/drive/items/${id}/content", InputStream::class.java)
        .buildRequest()
        .get()

    @Retryable
    fun getFiles(id: String): IDriveItemCollectionPage = graphClient.me().drive()
        .items(id)
        .children()
        .buildRequest(listOf())
        //.select(selectString)
        .get()

    private fun getRoot(): IDriveItemCollectionPage = graphClient
        .me().drive().root().children()
        .buildRequest()
        //.select(selectString)
        .get()

    private fun getItem(id: String): DriveItem = graphClient
        .me().drive().items(id)
        .buildRequest()
        .get()
}