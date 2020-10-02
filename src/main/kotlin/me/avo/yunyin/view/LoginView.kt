package me.avo.yunyin.view

import javafx.application.Platform
import javafx.scene.text.Font
import me.avo.yunyin.controller.LoginController
import me.avo.yunyin.enum.DataSourceType
import tornadofx.*

class LoginView : View("Login") {
    private val controller: LoginController by di()
    private val status: TaskStatus by inject()

    override val root = borderpane {
        setPrefSize(800.0, 600.0)

        top {
            label(title) {
                font = Font.font(22.0)
            }
        }

        center {
            vbox(spacing = 15) {
                button("OneDrive") {
                    action { controller.authenticate(DataSourceType.OneDrive) }
                }
                hbox(4.0) {
                    progressbar(status.progress)
                    label(status.message)
                    visibleWhen(status::running)
                    paddingAll = 4
                }
                button("Exit") {
                    setOnAction { Platform.exit() }
                }
            }
        }
    }

    init {
        controller.init(this)
    }
}