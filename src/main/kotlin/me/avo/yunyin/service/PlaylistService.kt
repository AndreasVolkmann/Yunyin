package me.avo.yunyin.service

import me.avo.yunyin.entity.Playlist
import me.avo.yunyin.entity.Track
import me.avo.yunyin.repository.PlaylistRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository
) {

    fun createTemporaryPlaylist(tracks: List<Track>): Playlist {
        return Playlist().apply {
            this.tracks = tracks
        }
    }

    fun createPlaylist(): Playlist {
        val playlist = Playlist().apply {
            name = "New"
        }
        return playlistRepository.saveAndFlush(playlist)
    }

    fun findAll(): List<Playlist> {
        return playlistRepository.findAll()
    }

    @Transactional
    fun getTracks(playlist: Playlist): List<Track> {
        return playlist.tracks
    }
}