package me.avo.yunyin.entity

import javax.persistence.*

@Entity
class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null


}