package me.avo.yunyin.service

import me.avo.yunyin.domain.DataSource
import me.avo.yunyin.entity.DataSourceKey
import me.avo.yunyin.entity.TrackStaging
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.factory.DataProviderFactory
import me.avo.yunyin.repository.ArtistRepository
import me.avo.yunyin.repository.TrackRepository
import me.avo.yunyin.repository.TrackStagingRepository
import me.avo.yunyin.service.provider.DataProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
internal class SynchronizationServiceTest(
    @Autowired private val trackRepository: TrackRepository,
    @Autowired private val artistRepository: ArtistRepository,
    @Autowired private val trackStagingRepository: TrackStagingRepository
) {

    class DataProviderFactoryMock(private val dataProvider: DataProvider) : DataProviderFactory {
        override fun getDataProvider(dataSource: DataSource) = dataProvider
    }

    class DataProviderMock(private val tracks: Collection<TrackStaging>) : DataProvider {
        private var isFirstCall = true
        override fun hasNext(): Boolean = isFirstCall

        override fun next(): Collection<TrackStaging> {
            isFirstCall = false
            return tracks
        }
    }

    private val defaultArtistName = "artist"
    private val dataSourceKey = DataSourceKey("1", DataSourceType.OneDrive)
    private val dataSource: DataSource = DataSource(dataSourceKey, "1")

    @Test fun `synchronize creates artist and track`() {
        // Given
        val tracks = listOf(makeTrackStaging("id"))

        // When
        getSynchronizationService(tracks).synchronize(dataSource)

        // Then
        val artists = artistRepository.findAll()
        expectThat(artists).isNotEmpty()
    }

    @Test fun `two tracks with same artist create one artist`() {
        // Given
        val tracks = listOf(
            makeTrackStaging("id", "a"),
            makeTrackStaging("id2", "b")
        )

        // When
        getSynchronizationService(tracks).synchronize(dataSource)

        // Then
        val artists = artistRepository.findAll()
        expectThat(artists).hasSize(1)
    }

    @Test fun `track should have artist id`() {
        // Given
        val tracks = listOf(makeTrackStaging("id", "a", defaultArtistName))

        // When
        getSynchronizationService(tracks).synchronize(dataSource)

        // Then
        val track = trackRepository.findAll().single()
        val artist = artistRepository.findByIdOrNull(track.artistId)!!
        expectThat(artist) {
            get { name }.isEqualTo(defaultArtistName)
        }
    }

    private fun getSynchronizationService(tracks: Collection<TrackStaging>) = SynchronizationService(
        DataProviderFactoryMock(DataProviderMock(tracks)),
        trackRepository, artistRepository, trackStagingRepository
    )

    private fun makeTrackStaging(
        itemId: String,
        title: String = "test",
        artist: String = defaultArtistName,
        album: String = "album",
    ) = TrackStaging().apply {
        this.title = title
        this.artist = artist
        this.album = album
        this.itemId = itemId
    }
}