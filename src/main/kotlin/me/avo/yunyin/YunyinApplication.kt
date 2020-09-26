package me.avo.yunyin

import javafx.application.Application
import me.avo.yunyin.controller.MainController
import me.avo.yunyin.view.LoginView
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import kotlin.reflect.KClass
import kotlin.system.exitProcess

@SpringBootApplication
class YunyinApplication : App(LoginView::class) {

    private lateinit var context: ConfigurableApplicationContext

    override fun init() {
        super.init()
        context = SpringApplication.run(this.javaClass)
        context.autowireCapableBeanFactory.autowireBean(this)
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = context.getBean(type.java)
        }
    }

    override fun stop() {
        super.stop()
        context.getBean(MainController::class.java).stop()
        context.close()
        exitProcess(0)
    }
}

fun main(args: Array<String>) = Application.launch(YunyinApplication::class.java, *args)
