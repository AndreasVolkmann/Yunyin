package me.avo.yunyin.factory

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.enum.DataSourceType
import me.avo.yunyin.service.provider.AuthProvider
import me.avo.yunyin.service.provider.DataProvider
import me.avo.yunyin.service.provider.StreamProvider

interface AuthProviderFactory {
    fun getAuthProvider(dataSourceType: DataSourceType): AuthProvider
}

interface DataProviderFactory {
    fun getDataProvider(dataSource: DataSource): DataProvider
}

interface StreamProviderFactory {
    fun getStreamProvider(dataSourceType: DataSourceType): StreamProvider
}