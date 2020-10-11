package me.avo.yunyin.service.provider

import me.avo.yunyin.domain.DataSource
import me.avo.yunyin.entity.DataSourceEntity
import me.avo.yunyin.entity.DataSourceKey
import me.avo.yunyin.repository.DataSourceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DataSourceService(
    private val dataSourceRepository: DataSourceRepository
) {

    fun register(dataSourceKey: DataSourceKey) {
        dataSourceRepository.findByIdOrNull(dataSourceKey)
            ?: DataSourceEntity(dataSourceKey).let(::save)
    }

    fun save(dataSource: DataSourceEntity): DataSourceEntity {
        return dataSourceRepository.saveAndFlush(dataSource)
    }

    fun save(dataSource: DataSource) {
        dataSourceRepository
            .findByIdOrNull(dataSource.id)
            ?.let {
                it.libraryId = dataSource.libraryId
                save(it)
            }
    }

    fun getAll(): List<DataSource> =
        dataSourceRepository
            .findAll()
            .map { DataSource(it.id!!, it.libraryId) }

    fun delete(dataSourceKey: DataSourceKey) {
        dataSourceRepository.deleteById(dataSourceKey)
    }
}