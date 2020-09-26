package me.avo.yunyin.service.provider

import me.avo.yunyin.entity.DataSource

interface DataProviderFactory {
    fun getDataProvider(dataSourceType: DataSource): DataProvider
}