package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.repository.ArtistRepository
import org.springframework.stereotype.Component
import tornadofx.runAsync
import tornadofx.ui

@Component
class ArtistBrowserController(
    private val artistRepository: ArtistRepository
) {
    val values: ObservableList<Artist> = FXCollections.observableArrayList()

    fun refresh() {
        runAsync {
            artistRepository.findAll()
        } ui {
            values.setAll(it)
        }
    }

}