package me.avo.yunyin.view

import me.avo.yunyin.controller.SettingsController
import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.model.DataSourceModel
import tornadofx.*

class SettingsView : View("Settings") {

    private val controller: SettingsController by di()

    override val root = vbox {

        borderpane {
            center {
                tableview(controller.dataSources) {
                    readonlyColumn("Id", DataSource::id)
                    readonlyColumn("Library Id", DataSource::libraryId)

                    controller.model.rebindOnChange(this) { selected ->
                        item = selected ?: DataSource()
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
                            controller.model.rollback()
                        }

                        button("Delete") {
                            action(controller::delete)
                        }
                    }
                }
            }        }
    }

    init {
        controller.load()
    }
}