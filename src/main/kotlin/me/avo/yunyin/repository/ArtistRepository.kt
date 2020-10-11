package me.avo.yunyin.repository

import me.avo.yunyin.entity.ARTIST_TABLE_NAME
import me.avo.yunyin.entity.ArtistEntity
import me.avo.yunyin.entity.TRACK_STAGING_TABLE_NAME
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArtistRepository : JpaRepository<ArtistEntity, Long> {

    @Modifying
    @Query(
        value = """
            insert into $ARTIST_TABLE_NAME(id, name) 
            select null AS id, t.artist
              from $TRACK_STAGING_TABLE_NAME t
             where not exists (select 'x' from $ARTIST_TABLE_NAME a where a.name = t.artist)
             group by t.artist
        """,
        nativeQuery = true
    )
    fun importFromTrackStaging()


}