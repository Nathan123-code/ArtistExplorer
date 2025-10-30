package com.nathan.artistexplorer.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import com.nathan.artistexplorer.data.model.AlbumResponse
import com.nathan.artistexplorer.data.model.ArtistResponse
import com.nathan.artistexplorer.data.model.TrackResponse

interface ArtistService {

    @GET("search.php")
    suspend fun searchArtist(@Query("s") artistName: String): ArtistResponse

    @GET("searchalbum.php")
    suspend fun getAlbums(@Query("s") artistName: String): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetail(@Query("m") albumId:String): AlbumResponse

    @GET("track.php")
    suspend fun getTracks(@Query("m") albumId: String): TrackResponse
}
