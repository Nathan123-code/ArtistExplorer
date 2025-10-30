package com.nathan.artistexplorer.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nathan.artistexplorer.data.model.Album
import com.nathan.artistexplorer.data.model.Artist
import com.nathan.artistexplorer.data.repository.ArtistRepository
import kotlinx.coroutines.launch

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {

    // State yang diamati oleh UI
    val artist = mutableStateOf<Artist?>(null)
    val albums = mutableStateOf<List<Album>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)


    fun loadArtist(name: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                error.value = null
                val artistResponse = repository.searchArtist(name)
                val foundArtist = artistResponse.artists?.firstOrNull()

                if (foundArtist != null) {
                    artist.value = foundArtist
                    val albumResponse = repository.getAlbums(name)
                    albums.value = albumResponse.album ?: emptyList()
                } else {
                    error.value = "Artist not found"
                    artist.value = null
                    albums.value = emptyList()
                }
            } catch (e: Exception) {
                error.value = e.message ?: "Failed to load data"
                artist.value = null
                albums.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}
