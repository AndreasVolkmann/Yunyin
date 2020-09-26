package me.avo.yunyin.service.provider

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.entity.DataSourceKey
import me.avo.yunyin.repository.DataSourceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DataSourceService(
    private val dataSourceRepository: DataSourceRepository
) {

    fun register(dataSourceKey: DataSourceKey): DataSource {
        return dataSourceRepository.findByIdOrNull(dataSourceKey)
            ?: newDataSource(dataSourceKey).let(::save)
    }

    fun save(dataSource: DataSource): DataSource {
        return dataSourceRepository.saveAndFlush(dataSource)
    }

    fun getAll(): List<DataSource> = dataSourceRepository.findAll()

    fun delete(dataSource: DataSource) {
        if (dataSource.id != null) {
            dataSourceRepository.delete(dataSource)
        }
    }

    private fun newDataSource(dataSourceKey: DataSourceKey) = DataSource().apply {
        id = dataSourceKey
    }
}