package me.avo.yunyin.service.provider.onedrive

import com.microsoft.aad.msal4j.*
import me.avo.yunyin.service.provider.AuthProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Service
class OneDriveAuthProvider(
    @Value("\${ms.app.id}")
    private val appId: String,
    @Value("\${ms.app.scopes}")
    private val appScopes: Array<String>,
    @Value("\${ms.app.authority}")
    private val appAuthority: String
) : AuthProvider {
    var authToken: String = ""
    private var validDate: Date? = null
    private var identity: String = ""

    override fun validTokenCached(): Boolean {
        val tokenFile = File("token")
        if (tokenFile.exists()) {
            val lines = tokenFile.readText().lines()
            val date = parseDate(lines[1])
            if (date.after(Date())) {
                authToken = lines[0]
                validDate = date
                identity = lines[2]
                return true
            }
        }

        return false
    }

    override fun authenticate() {
        val tokenFile = File("token")
        val result = getUserAccessToken()
        val token = result.accessToken()
        val expirationDate = result.expiresOnDate()
        tokenFile.writeText(listOf(token, formatDate(expirationDate), identity).joinToString("\n"))
    }

    override fun getIdentity(): String {
        return identity
    }

    private fun isValid(): Boolean = validDate?.after(Date()) ?: false

    private fun formatDate(date: Date): String = getDateFormat().format(date)

    private fun parseDate(dateString: String): Date = getDateFormat().parse(dateString)

    private fun getDateFormat() = SimpleDateFormat()

    private fun getUserAccessToken(): IAuthenticationResult {
        val pool = Executors.newFixedThreadPool(1)
        try {
            val app = PublicClientApplication.builder(appId)
                .authority(appAuthority)
                .executorService(pool)
                .build() // Build the MSAL application object with app ID and authority

            val accounts = app.accounts.get()
            val account = accounts.firstOrNull()

            val result = when {
                account != null -> acquireTokenSilently(app, account)
                else -> acquireTokenInteractive(app)
            }.exceptionally { ex: Throwable ->
                println("Unable to authenticate - ${ex.message}")
                throw ex
            }.join()

            authToken = result.accessToken()
            identity = result.account().username()
            validDate = result.expiresOnDate()
            return result
        } finally {
            pool.shutdown()
        }
    }

    private fun acquireTokenSilently(
        app: PublicClientApplication,
        account: IAccount
    ): CompletableFuture<IAuthenticationResult> {
        val authParams = SilentParameters
            .builder(appScopes.toSet(), account)
            .build()
        return app.acquireTokenSilently(authParams)
    }

    private fun acquireTokenInteractive(app: PublicClientApplication): CompletableFuture<IAuthenticationResult> {
        val authParams = InteractiveRequestParameters
            .builder(URI("http://localhost/login"))
            .systemBrowserOptions(SystemBrowserOptions.builder().openBrowserAction { url ->
                println("Browser action $url")
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $url")
            }.build())
            .scopes(appScopes.toSet())
            .build()
        return app.acquireToken(authParams)
    }
}