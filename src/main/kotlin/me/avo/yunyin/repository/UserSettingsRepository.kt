package me.avo.yunyin.repository

import me.avo.yunyin.entity.UserSettings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSettingsRepository : JpaRepository<UserSettings, Long>