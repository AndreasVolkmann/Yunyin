package me.avo.yunyin.service

import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.PlaylistEntity
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.repository.PlaylistRepository
import me.avo.yunyin.repository.TrackRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val trackRepository: TrackRepository
) {

    fun createPlaylist(): PlaylistEntity {
        val playlist = PlaylistEntity().apply {
            name = "New"
        }
        return playlistRepository.saveAndFlush(playlist)
    }

    fun findAll(): List<Playlist> {
        return playlistRepository
            .findAll()
            .map { Playlist(it.id, it.name ?: "") } // TODO
    }

    @Transactional
    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        trackRepository.findByIdOrNull(track.id)?.let { trackEntity ->
            playlistRepository.findByIdOrNull(playlist.id)?.let { playlistEntity ->
                playlistEntity.tracks.add(trackEntity)
                playlistRepository.saveAndFlush(playlistEntity)
            }
        }
    }

    @Transactional
    fun getTracks(playQueue: PlayQueue.PlaylistPlayQueue): List<TrackEntity> {
        val entity = playlistRepository.findByIdOrNull(playQueue.playlist.id)
        return entity?.let { it.tracks } ?: listOf()
    }
}