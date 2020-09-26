package me.avo.yunyin.repository

import me.avo.yunyin.entity.Track
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : JpaRepository<Track, Long> {

    @Modifying
    @Query(
        value = """
            insert into tracks(item_Id, title, artist, album) 
            select item_Id, title, artist, album
              from track_staging
        """,
        nativeQuery = true
    )
    fun insertStaging()

}