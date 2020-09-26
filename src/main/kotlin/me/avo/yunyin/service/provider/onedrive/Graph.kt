package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.logger.DefaultLogger
import com.microsoft.graph.logger.LoggerLevel
import com.microsoft.graph.models.extensions.IGraphServiceClient
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient

class Graph(private val accessToken: String) {

    val graphClient: IGraphServiceClient
    private val authProvider = SimpleAuthProvider(accessToken)

    init {
        // Create default logger to only log errors
        val logger = DefaultLogger()
        logger.loggingLevel = LoggerLevel.DEBUG

        // Build a me.avo.yunyin.service.auth.onedrive.Graph client
        graphClient = GraphServiceClient.builder()
            .authenticationProvider(authProvider)
            .logger(logger)
            .buildClient()
    }

    fun getUser(): User = graphClient // GET /me to get authenticated user
        .me()
        .buildRequest()
        .get()
}