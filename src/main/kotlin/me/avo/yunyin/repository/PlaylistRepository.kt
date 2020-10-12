package me.avo.yunyin.repository

import me.avo.yunyin.entity.PLAYLIST_TRACK_PID_NAME
import me.avo.yunyin.entity.PLAYLIST_TRACK_TABLE_NAME
import me.avo.yunyin.entity.PLAYLIST_TRACK_TID_NAME
import me.avo.yunyin.entity.PlaylistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {

    @Modifying
    @Query(
        nativeQuery = true,
        value = """
            INSERT INTO $PLAYLIST_TRACK_TABLE_NAME($PLAYLIST_TRACK_PID_NAME, $PLAYLIST_TRACK_TID_NAME)
            VALUES (?2, ?1)
        """
    )
    fun addTrackToPlaylist(trackId: Long, playlistId: Long)

}