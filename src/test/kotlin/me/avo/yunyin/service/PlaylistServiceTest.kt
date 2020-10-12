package me.avo.yunyin.service

import me.avo.yunyin.domain.PlayQueue
import me.avo.yunyin.domain.Track
import me.avo.yunyin.entity.TrackEntity
import me.avo.yunyin.enum.DataSourceType
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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
internal class PlaylistServiceTest(
    @Autowired private val playlistService: PlaylistService,
    @Autowired private val trackRepository: TrackRepository
) {

    @BeforeAll fun beforeAll() {
        playlistService.createPlaylist()
    }

    @Test fun `addTrackToPlaylist - adds track to playlist`() {
        // Given
        val trackEntity = makeTrack("1")
        val track = Track(trackEntity.id!!, "", "", 0, "", DataSourceType.OneDrive, 0)
        val playlist = playlistService.findAll().first()

        // When
        playlistService.addTrackToPlaylist(track, playlist)

        // Then
        val tracks = playlistService.getTracks(PlayQueue.PlaylistPlayQueue(playlist))
        expectThat(tracks) {
            hasSize(1).and {
                get(0).get { id }.isEqualTo(track.id)
            }
        }
    }

    private fun makeTrack(
        itemId: String,
        title: String = "test",
        album: String = "album",
    ) = TrackEntity(null, itemId, title, 1, album).let(trackRepository::saveAndFlush)
}