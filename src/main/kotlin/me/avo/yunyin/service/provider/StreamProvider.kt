package me.avo.yunyin.service.provider

import me.avo.yunyin.domain.Track
import java.io.InputStream

interface StreamProvider {

    fun getStream(track: Track): InputStream
}