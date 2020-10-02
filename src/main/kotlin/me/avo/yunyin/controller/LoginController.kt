package me.avo.yunyin.controller

import javafx.util.Duration
import me.avo.yunyin.entity.DataSourceKey
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.service.provider.AuthProviderFactory
import me.avo.yunyin.service.provider.DataSourceService
import me.avo.yunyin.view.LoginView
import me.avo.yunyin.view.MainView
import org.springframework.stereotype.Component
import tornadofx.Controller
import tornadofx.ViewTransition

@Component
class LoginController(
    private val authProviderFactory: AuthProviderFactory,
    private val dataSourceService: DataSourceService
) : Controller() {

    private lateinit var loginView: LoginView

    fun init(loginView: LoginView) {
        this.loginView = loginView
    }

    fun authenticate(dataSourceType: DataSourceType) {
        val authProvider = authProviderFactory.getAuthProvider(dataSourceType)
        runAsync {
            updateMessage("Logging in ...")
            updateProgress(-1, -1)
            if (!authProvider.validTokenCached()) {
                authProvider.authenticate()
            }
        } ui {
            val key = DataSourceKey(authProvider.getIdentity(), dataSourceType)
            dataSourceService.register(key)
            println("Logged in!")
            loginView.replaceWith<MainView>(ViewTransition.Metro(Duration.seconds(2.5)))
        }
    }
}