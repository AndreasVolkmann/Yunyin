package me.avo.yunyin.view

import tornadofx.View
import tornadofx.label
import tornadofx.listmenu
import tornadofx.vbox

class SideBarView : View() {
    override val root = vbox {
        listmenu(theme = "blue") {
            item(text = "Library") {
                // Marks this item as active.
                activeItem = this
                whenSelected { /* Do some action */ }
            }
            item(text = "Browse")
        }

        label("Playlists")



    }
}