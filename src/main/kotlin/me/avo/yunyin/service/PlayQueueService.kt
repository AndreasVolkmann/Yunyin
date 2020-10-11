package me.avo.yunyin.service

import me.avo.yunyin.domain.Artist
import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.repository.TrackRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlayQueueService(
    private val playlistService: PlaylistService,
    private val trackRepository: TrackRepository
) {

    fun playQueueForArtist(artist: Artist?): PlayQueue {
        return artist
            ?.let(PlayQueue::ArtistPlayQueue)
            ?: PlayQueue.EmptyPlayQueue
    }

    fun playQueueForPlaylist(playlist: Playlist?): PlayQueue {
        return playlist?.let(PlayQueue::PlaylistPlayQueue)
            ?: PlayQueue.EmptyPlayQueue
    }

    @Transactional
    fun getTracks(playQueue: PlayQueue): List<Track> =
        when (playQueue) {
            is PlayQueue.PlaylistPlayQueue -> {
                playlistService.getTracks(playQueue)
            }
            is PlayQueue.ArtistPlayQueue -> {
                val artistId = playQueue.artist.id
                trackRepository.findAllByArtistId(artistId)
            }
            PlayQueue.EmptyPlayQueue -> {
                listOf()
            }
        }.mapIndexed { index, entity -> entity.toTrack(index) }

    private fun TrackEntity.toTrack(index: Int) = Track(
        id!!, itemId!!, title!!, artistId!!, album!!,
        DataSourceType.OneDrive, // TODO
        index
    )
}