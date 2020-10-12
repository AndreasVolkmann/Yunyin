package me.avo.yunyin.domain

import me.avo.yunyin.enum.DataSourceType

data class Track(
    val id: Long,
    val itemId: String,
    val title: String,
    val artistId: Long,
    val album: String?,
    val dataSourceType: DataSourceType,
    val index: Int
)