package me.avo.yunyin.service.provider.onedrive

import com.microsoft.graph.authentication.IAuthenticationProvider
import com.microsoft.graph.http.IHttpRequest

class SimpleAuthProvider(
    private val accessToken: String
) : IAuthenticationProvider {

    override fun authenticateRequest(request: IHttpRequest) {
        request.addHeader("Authorization", "Bearer $accessToken")
    }
}