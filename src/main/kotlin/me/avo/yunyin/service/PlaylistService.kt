package me.avo.yunyin.service

import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.PlaylistEntity
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.repository.PlaylistRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository
) {

    fun createPlaylist(): PlaylistEntity {
        val playlist = PlaylistEntity(name = "New")
        return playlistRepository.saveAndFlush(playlist)
    }

    fun findAll(): List<Playlist> {
        return playlistRepository
            .findAll()
            .map { Playlist(it.id!!, it.name) }
    }

    @Transactional
    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        playlistRepository.addTrackToPlaylist(track.id, playlist.id)
    }

    @Transactional
    fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        playlistRepository.removeTrackFromPlaylist(track.id, playlist.id)
    }

    @Transactional
    fun getTracks(playQueue: PlayQueue.PlaylistPlayQueue): List<TrackEntity> {
        val entity = playlistRepository.findByIdOrNull(playQueue.playlist.id)
        return entity
            ?.let(PlaylistEntity::tracks)
            ?: listOf()
    }
}