package me.avo.yunyin.entity

import javax.persistence.*

const val TRACK_TABLE_NAME = "tracks"

@Entity
@Table(name = TRACK_TABLE_NAME)
class TrackEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var itemId: String,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var artistId: Long,

    @Column(nullable = true)
    var album: String? = null
)