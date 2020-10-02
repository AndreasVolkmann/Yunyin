package me.avo.yunyin.controller

import com.microsoft.graph.models.extensions.DriveItem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import me.avo.yunyin.model.FileItem
import me.avo.yunyin.service.UserSettingsService
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
class MainController(
//    private val fileSystemController: FileSystemController,
    private val audioController: AudioController,
    private val userSettingsService: UserSettingsService
) : Controller() {

//    private val fileSelect = fileSystemController.fileSelect
    val values: ObservableList<FileItem> = FXCollections.observableArrayList()
    val selectedItem = SimpleObjectProperty<FileItem>()
    val currentFolderName = SimpleStringProperty("Root")
    val canGoBack = SimpleBooleanProperty(false)

    fun load() {
        runAsync {
            val userSettings = userSettingsService.getUserSettings()
            println("UserSettings library folder id: $userSettings")
//            fileSystemController.load(userSettings.libraryFolderId) ui ::setValue
        }
    }

    fun stop() {

    }

    fun back() {
//        fileSystemController.back() ui ::setValue
    }

    fun selectItem() {
        if (selectedItem.isNotNull.value) {
            val fileItem = selectedItem.get()

            when {
                //fileItem.isAudio -> audioController.playAudio(fileItem)
                fileItem.driveItem.folder != null -> loadFolder(fileItem)
            }
        }
    }

    fun setLibraryFolder() {
        if (selectedItem.isNotNull.value) {
            userSettingsService.setLibraryFolder(selectedItem.get())
        }
    }

    private fun loadFolder(fileItem: FileItem) {
//        runAsync {
//            fileSystemController.selectItem(fileItem.driveItem).currentPage
//        } ui ::setValue
    }

    private fun setValue(driveItems: List<DriveItem>) {
//        canGoBack.value = fileSelect.canGoBack()
//        currentFolderName.value = fileSelect.currentFolder?.name ?: "Root"
        driveItems
            .mapIndexed(::FileItem)
            .let(values::setAll)
    }
}
