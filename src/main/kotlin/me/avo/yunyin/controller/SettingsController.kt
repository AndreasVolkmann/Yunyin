package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.domain.DataSource
import me.avo.yunyin.model.DataSourceModel
import me.avo.yunyin.service.SynchronizationService
import me.avo.yunyin.service.provider.DataSourceService
import org.springframework.stereotype.Component
import tornadofx.runAsync
import tornadofx.success
import tornadofx.ui

@Component
class SettingsController(
    private val dataSourceService: DataSourceService,
    private val synchronizationService: SynchronizationService
) {
    val dataSources: ObservableList<DataSource> = FXCollections.observableArrayList()
    val model = DataSourceModel(null)

    fun load() {
        runAsync {
            dataSourceService.getAll()
        } ui dataSources::setAll
    }

    fun save() {
        model.item?.let {
            model.commit()
            runAsync {
                dataSourceService.save(it)
            } success {
                load()
            }
        }
    }

    fun delete() {
        model.item?.let {
            runAsync {
                dataSourceService.delete(it.id)
            } success {
                load()
            }
        }
    }

    fun synchronize() {
        val dataSource = model.item
        runAsync {
            synchronizationService.synchronize(dataSource)
        }
    }
}