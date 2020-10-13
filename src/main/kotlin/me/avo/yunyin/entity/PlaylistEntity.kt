package me.avo.yunyin.entity

import javax.persistence.*

const val PLAYLIST_TRACK_TABLE_NAME = "playlist_track"
const val PLAYLIST_TRACK_PID_NAME = "playlist_id"
const val PLAYLIST_TRACK_TID_NAME = "track_id"

@Entity
@Table(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    @JoinTable(
        name = PLAYLIST_TRACK_TABLE_NAME,
        joinColumns = [JoinColumn(name = PLAYLIST_TRACK_PID_NAME)],
        inverseJoinColumns = [JoinColumn(name = PLAYLIST_TRACK_TID_NAME)]
    )
    var tracks: List<TrackEntity> = emptyList()
)