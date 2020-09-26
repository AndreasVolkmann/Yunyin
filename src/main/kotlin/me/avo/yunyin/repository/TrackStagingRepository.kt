package me.avo.yunyin.repository

import me.avo.yunyin.entity.TrackStaging
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackStagingRepository : JpaRepository<TrackStaging, Long> {
}