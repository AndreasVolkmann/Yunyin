package me.avo.yunyin.entity

import me.avo.yunyin.enum.DataSourceType
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class DataSource(
    @EmbeddedId
    val id: DataSourceKey? = null,

    @Column(nullable = true)
    var libraryId: String? = null
) {
    override fun toString(): String = "DataSource($id, libraryId: $libraryId)"
}

@Embeddable
data class DataSourceKey(
    @Column(nullable = false)
    var providerIdentity: String,
    @Column(nullable = false)
    var dataSourceType: DataSourceType
) : Serializable
