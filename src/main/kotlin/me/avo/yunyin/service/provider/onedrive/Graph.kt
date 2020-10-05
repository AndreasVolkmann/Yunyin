package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.logger.DefaultLogger
import com.microsoft.graph.logger.LoggerLevel
import com.microsoft.graph.models.extensions.IGraphServiceClient
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient

class Graph(private val accessToken: String) {

    val graphClient: IGraphServiceClient = GraphServiceClient.builder()
        .authenticationProvider {
            it.addHeader("Authorization", "Bearer $accessToken")
        }
        .logger(DefaultLogger().apply {
            loggingLevel = LoggerLevel.DEBUG
        })
        .buildClient()

    fun getUser(): User = graphClient // GET /me to get authenticated user
        .me()
        .buildRequest()
        .get()
}