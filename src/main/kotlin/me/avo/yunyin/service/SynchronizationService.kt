package me.avo.yunyin.service

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.repository.ArtistRepository
import me.avo.yunyin.repository.TrackRepository
import me.avo.yunyin.repository.TrackStagingRepository
import me.avo.yunyin.service.provider.DataProviderFactory
import me.avo.yunyin.service.provider.DataSourceService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SynchronizationService(
    private val dataProviderFactory: DataProviderFactory,
    private val dataSourceService: DataSourceService,
    private val trackRepository: TrackRepository,
    private val artistRepository: ArtistRepository,
    private val trackStagingRepository: TrackStagingRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun synchronize() {
        loadStaging()
        synchronizeTracks()
    }

    private fun loadStaging() {
        dataSourceService
            .getAll()
            .forEach(::loadDataSourceIntoStaging)
    }

    private fun loadDataSourceIntoStaging(dataSource: DataSource) {
        val id = dataSource.id!!
        val libraryId = dataSource.libraryId
        if (libraryId != null) {
            logger.info("Fetching data for $id")
            val dataProvider = dataProviderFactory.getDataProvider(dataSource)
            dataProvider.forEach { tracks ->
                logger.info("Found ${tracks.size} tracks")
                if (tracks.isNotEmpty()) {
                    trackStagingRepository.saveAll(tracks)
                    trackStagingRepository.flush()
                }
            }
        }
        else {
            logger.info("DataSource library id has not been set for: $id. Skipping synchronization.")
        }
    }

    private fun synchronizeTracks() {
        trackRepository.insertStaging()
    }
}