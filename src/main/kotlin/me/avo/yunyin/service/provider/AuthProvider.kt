package me.avo.yunyin.service.provider

interface AuthProvider {

    /**
     * Whether a valid token exists in the cache.
     */
    fun validTokenCached(): Boolean

    /**
     * Authenticate with the provider.
     */
    fun authenticate()

    /**
     * The unique identity associated with this authentication provider.
     */
    fun getIdentity(): String

}