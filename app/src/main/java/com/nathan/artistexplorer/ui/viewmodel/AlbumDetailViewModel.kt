package com.nathan.artistexplorer.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nathan.artistexplorer.data.model.Track
import com.nathan.artistexplorer.data.model.Album
import com.nathan.artistexplorer.data.repository.ArtistRepository
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val repository: ArtistRepository) : ViewModel() {

    val album = mutableStateOf<Album?>(null)
    val tracks = mutableStateOf<List<Track>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)


    fun loadAlbumDetail(selectedAlbum: Album) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                error.value = null
                album.value = selectedAlbum
                val trackResponse = repository.getTracks(selectedAlbum.idAlbum ?: "")
                tracks.value = trackResponse.track ?: emptyList()

            } catch (e: Exception) {
                error.value = e.message ?: "Failed to load track list"
                tracks.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}
