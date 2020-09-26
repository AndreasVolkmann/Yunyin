package me.avo.yunyin.service

import me.avo.yunyin.entity.*
import me.avo.yunyin.repository.ArtistRepository
import me.avo.yunyin.repository.TrackRepository
import me.avo.yunyin.repository.TrackStagingRepository
import me.avo.yunyin.service.provider.DataProvider
import me.avo.yunyin.service.provider.DataProviderFactory
import me.avo.yunyin.service.provider.DataSourceService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.isEqualTo

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SynchronizationServiceTest(
    @Autowired private val trackRepository: TrackRepository,
    @Autowired private val dataSourceService: DataSourceService,
    @Autowired private val artistRepository: ArtistRepository,
    @Autowired private val trackStagingRepository: TrackStagingRepository
) {

    class DataProviderFactoryMock(private val dataProvider: DataProvider) : DataProviderFactory {
        override fun getDataProvider(dataSourceType: DataSource) = dataProvider
    }

    class DataProviderMock(private val tracks: Collection<TrackStaging>) : DataProvider {
        override fun fetch(libraryId: String) = tracks
    }

    @Test fun test() {
        // Given
        val expectedTitle = "Mock"
        val key = DataSourceKey("1", DataSourceType.OneDrive)
        dataSourceService
            .register(key)
            .apply { libraryId = "1" }
            .let(dataSourceService::save)
        val tracks = listOf(TrackStaging().apply {
            title = expectedTitle
            artist = "MockArt"
            album = "o"
            itemId = "id"
        })
        val synchronizationService = SynchronizationService(
            DataProviderFactoryMock(DataProviderMock(tracks)),
            dataSourceService, trackRepository, artistRepository, trackStagingRepository
        )

        // When
        synchronizationService.synchronize()

        // Then
        expectThat(trackRepository.findAll()) {
            get { size }.isEqualTo(1)
            get(0).and {
                get { title }.isEqualTo(expectedTitle)
            }
        }
    }
}