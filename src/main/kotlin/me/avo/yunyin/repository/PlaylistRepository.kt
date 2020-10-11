package me.avo.yunyin.repository

import me.avo.yunyin.entity.PlaylistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaylistRepository : JpaRepository<PlaylistEntity, Long>