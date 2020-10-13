package me.avo.yunyin.service

import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Playlist
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.PlaylistEntity
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.repository.PlaylistRepository
import me.avo.yunyin.repository.TrackRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
internal class PlaylistServiceTest(
    @Autowired private val playlistService: PlaylistService,
    @Autowired private val playlistRepository: PlaylistRepository,
    @Autowired private val trackRepository: TrackRepository
) {
    private var trackEntity = makeTrack("1")
    private lateinit var track: Track
    private lateinit var playlist: Playlist

    @BeforeAll fun beforeAll() {
        playlistService.createPlaylist()
        playlist = playlistService.findAll().first()
        trackEntity.let(trackRepository::saveAndFlush)
        track = Track(trackEntity.id!!, "1", "1", 0, null, DataSourceType.OneDrive, 0)
    }

    @Test fun `addTrackToPlaylist - adds track to playlist`() {
        // When
        playlistService.addTrackToPlaylist(track, playlist)

        // Then
        expectThat(getPlaylistTracks()) {
            hasSize(1) and {
                get(0).get { id }.isEqualTo(track.id)
            }
        }
    }

    @Test fun `removeTrackFromPlaylist - `() {
        // Given
        val playlistEntity = PlaylistEntity(null, "Test", listOf(trackEntity))
        playlistRepository.saveAndFlush(playlistEntity)
        // TODO

        // When
        playlistService.removeTrackFromPlaylist(track, playlist)

        // Then
        expectThat(getPlaylistTracks()) {
            hasSize(0)
        }
    }

    private fun getPlaylistTracks() = playlistService.getTracks(PlayQueue.PlaylistPlayQueue(playlist))

    private fun makeTrack(
        itemId: String,
        title: String = "test",
        album: String = "album",
    ) = TrackEntity(null, itemId, title, 1, album)
}