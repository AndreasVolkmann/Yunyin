package me.avo.yunyin.domain

import me.avo.yunyin.entity.DataSourceKey

data class DataSource(
    val id: DataSourceKey,
    var libraryId: String?
)