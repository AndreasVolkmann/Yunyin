package me.avo.yunyin.entity

import javax.persistence.*

@Entity
@Table(name = "track_staging")
class TrackStaging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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