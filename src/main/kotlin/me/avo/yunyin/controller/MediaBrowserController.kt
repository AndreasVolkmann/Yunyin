package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.entity.Track
import me.avo.yunyin.repository.TrackRepository
import me.avo.yunyin.service.SynchronizationService
import org.springframework.stereotype.Component
import tornadofx.runAsync
import tornadofx.success
import tornadofx.ui

@Component
class MediaBrowserController(
    private val trackRepository: TrackRepository,
    private val synchronizationService: SynchronizationService
) {
    val values: ObservableList<Track> = FXCollections.observableArrayList()

    fun init() {
        runAsync {
            synchronizationService.synchronize()

        } success {
            refresh()
        }
    }

    fun refresh() {
        runAsync {
            trackRepository.findAll()
        } ui {
            values.setAll(it)
        }
    }

}