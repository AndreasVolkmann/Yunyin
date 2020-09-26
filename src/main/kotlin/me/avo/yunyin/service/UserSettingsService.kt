package me.avo.yunyin.service

import me.avo.yunyin.entity.UserSettings
import me.avo.yunyin.model.FileItem
import me.avo.yunyin.repository.UserSettingsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSettingsService(
    private val userSettingsRepository: UserSettingsRepository
) {

    fun getUserSettings(): UserSettings {
        val result = userSettingsRepository.findById(1)
        return if (result.isPresent) result.get()
        else UserSettings()
    }

    @Transactional
    fun setLibraryFolder(fileItem: FileItem) {
        val userSettings = getUserSettings()
        userSettings.libraryFolderId = fileItem.driveItem.id
        println("Set library folder id $userSettings")
        userSettingsRepository.saveAndFlush(userSettings)
    }

}