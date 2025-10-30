package com.nathan.artistexplorer.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nathan.artistexplorer.data.model.Album
import com.nathan.artistexplorer.data.model.Track
import com.nathan.artistexplorer.ui.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(viewModel: AlbumDetailViewModel, album: Album) {
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value
    val tracks = viewModel.tracks.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF232323)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(0.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = album.strAlbum ?: "",
                fontSize = 22.sp,
                color = Color(0xFFD6C27A),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            // Combined card for image and description
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(0.5.dp, Color(0xFF444444))
            ) {
                Column {
                    AsyncImage(
                        model = album.strAlbumThumb,
                        contentDescription = album.strAlbum,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(18.dp))
                    )
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = album.strAlbum ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD6C27A)
                        )
                        Text(
                            text = "${album.intYearReleased ?: ""} • ${album.strGenre ?: ""}",
                            fontSize = 14.sp,
                            color = Color(0xFFB0AFAF)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = album.strDescriptionEN ?: "No description available.",
                            fontSize = 14.sp,
                            color = Color(0xFFEDEDED)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tracks",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD6C27A),
                modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
            )

            if (isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFD6C27A),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (error != null) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            } else {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    tracks.forEachIndexed { index, track ->
                        TrackRow(index + 1, track)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TrackRow(number: Int, track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFD6C27A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                color = Color(0xFF232323),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = track.strTrack ?: "",
            color = Color(0xFFEDEDED),
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = track.strDuration ?: "",
            color = Color(0xFFB0AFAF),
            fontSize = 14.sp
        )
    }
}
