package me.avo.yunyin.entity

import javax.persistence.*

@Entity
class PlaylistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    var trackId: Long? = null

    @Column(nullable = false)
    var playlistId: Long? = null

}