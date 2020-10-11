package me.avo.yunyin.repository

import me.avo.yunyin.entity.ARTIST_TABLE_NAME
import me.avo.yunyin.entity.TRACK_STAGING_TABLE_NAME
import me.avo.yunyin.entity.TRACK_TABLE_NAME
import me.avo.yunyin.entity.TrackEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : JpaRepository<TrackEntity, Long> {

    @Modifying
    @Query(
        value = """
            insert into $TRACK_TABLE_NAME(id, item_id, title, artist_id, album) 
            select null AS id, item_Id, title, a.id, album
              from $TRACK_STAGING_TABLE_NAME t
              join $ARTIST_TABLE_NAME a on a.name = t.artist
        """,
        nativeQuery = true
    )
    fun insertStaging()

    fun findAllByArtistId(artistId: Long): List<TrackEntity>

}