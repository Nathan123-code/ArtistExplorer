package com.nathan.artistexplorer.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.nathan.artistexplorer.ui.viewmodel.ArtistViewModel

private val DarkBg = Color(0xFF232323)
private val DarkCard = Color(0xFF2C2C2C)
private val GoldAccent = Color(0xFFD6C27A)
private val Muted = Color(0xFFB0AFAF)
private val CardOutline = Color(0xFF444444)
private val LightText = Color(0xFFEDEDED)

@Composable
fun ArtistScreen(
    viewModel: ArtistViewModel,
    onAlbumClick: (Album) -> Unit
) {
    var artistName by remember { mutableStateOf("") }
    val artist = viewModel.artist.value
    val albums = viewModel.albums.value

    Surface(modifier = Modifier.fillMaxSize(), color = DarkBg) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = artistName,
                onValueChange = { artistName = it },
                label = { Text("Search artist", color = Muted) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = LightText,
                    unfocusedTextColor = LightText,
                    focusedBorderColor = CardOutline,
                    unfocusedBorderColor = CardOutline,
                    cursorColor = GoldAccent
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { if (artistName.isNotBlank()) viewModel.loadArtist(artistName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
            ) {
                Text("Search", color = DarkBg)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = artist?.strArtist ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = LightText,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AsyncImage(
                        model = artist?.strArtistThumb,
                        contentDescription = artist?.strArtist,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()

                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = artist?.strArtist ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightText
                        )
                        Text(
                            text = artist?.strGenre ?: "",
                            fontSize = 14.sp,
                            color = Muted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                "Albums",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LightText,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 18.dp, bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f) // Ensures grid is scrollable
            ) {
                items(albums) { album ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCard),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onAlbumClick(album) }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            AsyncImage(
                                model = album.strAlbumThumb,
                                contentDescription = album.strAlbum,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                album.strAlbum ?: "",
                                fontWeight = FontWeight.Bold,
                                color = LightText,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                "${album.intYearReleased ?: ""} • ${album.strGenre ?: ""}",
                                color = Muted,
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
