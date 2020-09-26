package me.avo.yunyin.model

import me.avo.yunyin.entity.DataSource
import tornadofx.ItemViewModel

class DataSourceModel(
    dataSource: DataSource
) : ItemViewModel<DataSource>(dataSource) {
    val id = bind(DataSource::id)
    val libraryId = bind(DataSource::libraryId)
}
