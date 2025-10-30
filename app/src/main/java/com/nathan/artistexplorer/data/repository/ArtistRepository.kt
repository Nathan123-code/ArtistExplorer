package com.nathan.artistexplorer.data.repository

import com.nathan.artistexplorer.data.service.ArtistService

class ArtistRepository(private val api: ArtistService) {
    suspend fun searchArtist(name: String) = api.searchArtist(name)
    suspend fun getAlbums(name: String) = api.getAlbums(name)
    suspend fun getTracks(id: String) = api.getTracks(id)
}