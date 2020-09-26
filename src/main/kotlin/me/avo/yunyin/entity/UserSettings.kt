package me.avo.yunyin.entity

import javax.persistence.*

@Entity
class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = true)
    var libraryFolderId: String? = null

    override fun toString(): String {
        return "id: ${id}, libFolderId: $libraryFolderId"
    }

}