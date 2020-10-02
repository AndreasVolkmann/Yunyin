package me.avo.yunyin.service.provider

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.enum.DataSourceType

interface AuthProviderFactory {
    fun getAuthProvider(dataSourceType: DataSourceType): AuthProvider
}

interface DataProviderFactory {
    fun getDataProvider(dataSource: DataSource): DataProvider
}

interface StreamProviderFactory {
    fun getStreamProvider(dataSourceType: DataSourceType): StreamProvider
}