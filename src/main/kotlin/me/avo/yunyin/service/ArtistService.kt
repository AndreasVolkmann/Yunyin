package me.avo.yunyin.service

import me.avo.yunyin.domain.Artist
import me.avo.yunyin.entity.ArtistEntity
import me.avo.yunyin.repository.ArtistRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArtistService(
    private val artistRepository: ArtistRepository
) {

    fun findArtistById(id: Long): Artist? {
        return artistRepository.findByIdOrNull(id)?.let { it.toArtist() }
    }

    fun findAll(): List<Artist> =
        artistRepository
            .findAll()
            .map { it.toArtist() }

    private fun ArtistEntity.toArtist() = Artist(
        id!!, name
    )
}