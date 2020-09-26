package me.avo.yunyin.repository

import me.avo.yunyin.entity.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtistRepository : JpaRepository<Artist, Long>