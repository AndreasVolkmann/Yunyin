package me.avo.yunyin.entity

import javax.persistence.*

@Entity
class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var tracks: List<Track> = listOf()

}