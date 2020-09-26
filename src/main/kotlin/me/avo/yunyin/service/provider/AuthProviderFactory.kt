package me.avo.yunyin.service.provider

import me.avo.yunyin.entity.DataSourceType

interface AuthProviderFactory {

    fun getAuthProvider(dataSourceType: DataSourceType): AuthProvider
}