package me.avo.yunyin.view

import me.avo.yunyin.controller.SettingsController
import me.avo.yunyin.domain.DataSource
import tornadofx.*

class SettingsView : View("Settings") {

    private val controller: SettingsController by di()

    override val root = vbox {
        borderpane {
            center {
                tableview(controller.dataSources) {
                    readonlyColumn("Id", DataSource::id)
                    readonlyColumn("Library Id", DataSource::libraryId)

                    controller.model.rebindOnChange(this) {
                        item = it
                    }
                }
            }

            right {
                form {
                    fieldset("Edit data source") {
                        field("Library Id") {
                            textfield(controller.model.libraryId)
                        }

                        button("Save") {
                            enableWhen(controller.model.dirty)
                            action(controller::save)
                        }
                        button("Reset").action {
                            enableWhen(controller.model.dirty)
                            controller.model.rollback()
                        }

                        button("Delete") {
                            enableWhen(controller.model.empty.not())
                            action(controller::delete)
                        }

                        button("Synchronize") {
                            enableWhen(controller.model.empty.not())
                            action(controller::synchronize)
                        }
                    }
                }
            }
        }
    }

    init {
        controller.load()
    }
}