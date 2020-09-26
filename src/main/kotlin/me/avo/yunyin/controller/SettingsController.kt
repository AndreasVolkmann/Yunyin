package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.model.DataSourceModel
import me.avo.yunyin.service.provider.DataSourceService
import org.springframework.stereotype.Component
import tornadofx.runAsync
import tornadofx.success
import tornadofx.ui

@Component
class SettingsController(
    private val dataSourceService: DataSourceService
) {
    val dataSources: ObservableList<DataSource> = FXCollections.observableArrayList()
    val model = DataSourceModel(DataSource())

    fun load() {
        runAsync {
            dataSourceService.getAll()
        } ui {
            dataSources.setAll(it)
        }
    }

    fun save() {
        model.commit()
        runAsync {
            dataSourceService.save(model.item)
        } success {
            load()
        }
    }

    fun delete() {
        runAsync {
            dataSourceService.delete(model.item)
        } success {
            load()
        }
    }
}