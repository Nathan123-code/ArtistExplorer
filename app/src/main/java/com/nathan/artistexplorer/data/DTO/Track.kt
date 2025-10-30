package com.nathan.artistexplorer.data.model

data class TrackResponse(
    val track: List<Track>?
)

data class Track(
    val idTrack: String?,
    val strTrack: String?,
    val intTrackNumber: String?,
    val strDuration: String?
)
