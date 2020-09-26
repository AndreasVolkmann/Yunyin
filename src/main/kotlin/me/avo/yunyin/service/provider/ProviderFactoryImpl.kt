package me.avo.yunyin.service.provider

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.entity.DataSourceType
import me.avo.yunyin.service.provider.onedrive.OneDriveGraph
import me.avo.yunyin.service.provider.onedrive.OneDriveAuthProvider
import me.avo.yunyin.service.provider.onedrive.OneDriveDataProvider
import org.springframework.stereotype.Service

@Service
class ProviderFactoryImpl(
    private val oneDriveAuthProvider: OneDriveAuthProvider
) : AuthProviderFactory, DataProviderFactory {

    override fun getAuthProvider(dataSourceType: DataSourceType): AuthProvider {
        return when (dataSourceType) {
            DataSourceType.OneDrive -> oneDriveAuthProvider
        }
    }

    override fun getDataProvider(dataSource: DataSource): DataProvider {
        val id = dataSource.id ?: throw IllegalArgumentException("DataSource id has to be set")

        return when (id.dataSourceType) {
            DataSourceType.OneDrive -> OneDriveDataProvider(
                OneDriveGraph(oneDriveAuthProvider),
                dataSource
            )
        }
    }

}