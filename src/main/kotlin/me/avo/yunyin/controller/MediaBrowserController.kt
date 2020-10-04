package me.avo.yunyin.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import me.avo.yunyin.entity.Artist
import me.avo.yunyin.entity.Track
import me.avo.yunyin.model.TrackFilterModel
import me.avo.yunyin.repository.TrackRepository
import org.springframework.stereotype.Component
import tornadofx.onChange
import tornadofx.runAsync
import tornadofx.ui

@Component
class MediaBrowserController(
    private val trackRepository: TrackRepository
) {
    val values: ObservableList<Track> = FXCollections.observableArrayList()

    fun init(model: TrackFilterModel) {
        refresh(null)
        model.itemProperty.onChange {
            refresh(it)
        }
    }

    fun refresh(artist: Artist?): Task<List<Track>> = runAsync {
        artist?.id
            ?.let { trackRepository.findAllByArtistId(it) }
            ?: trackRepository.findAll()
    } ui values::setAll
}