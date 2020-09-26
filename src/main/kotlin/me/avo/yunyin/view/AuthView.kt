package me.avo.yunyin.view

import tornadofx.View
import tornadofx.webview

class AuthView(url: String) : View("Authentication") {
    override val root = webview {
        prefWidth = 1000.0
        engine.load(url)
        println("Web view")
    }

}