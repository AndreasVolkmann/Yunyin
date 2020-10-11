package me.avo.yunyin.entity

import javax.persistence.*

@Entity
@Table(name = "playlists")
class PlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var tracks: MutableList<TrackEntity> = mutableListOf()

}