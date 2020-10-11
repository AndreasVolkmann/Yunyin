package me.avo.yunyin.entity

import javax.persistence.*

const val ARTIST_TABLE_NAME = "artists"

@Entity
@Table(
    name = ARTIST_TABLE_NAME,
    indexes = [Index(name = "artist_name_idx", columnList = "name", unique = true)]
)
class ArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String
)