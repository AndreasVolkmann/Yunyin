package me.avo.yunyin.entity

import javax.persistence.*

const val TRACK_STAGING_TABLE_NAME = "track_staging"

@Entity
@Table(name = TRACK_STAGING_TABLE_NAME)
class TrackStaging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var itemId: String? = null

    @Column(nullable = false)
    var title: String? = null

    @Column(nullable = true)
    var artist: String? = null

    @Column(nullable = true)
    var album: String? = null

}