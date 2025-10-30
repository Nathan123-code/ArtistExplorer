package com.nathan.artistexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nathan.artistexplorer.data.repository.ArtistRepository
import com.nathan.artistexplorer.data.service.RetrofitInstance
import com.nathan.artistexplorer.ui.theme.ArtistExplorerTheme
import com.nathan.artistexplorer.ui.view.AlbumDetailScreen
import com.nathan.artistexplorer.ui.view.ArtistScreen
import com.nathan.artistexplorer.ui.viewmodel.AlbumDetailViewModel
import com.nathan.artistexplorer.ui.viewmodel.ArtistViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ArtistRepository(RetrofitInstance.api)
        val artistViewModel = ArtistViewModel(repository)
        val albumDetailViewModel = AlbumDetailViewModel(repository)

        setContent {
            ArtistExplorerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "artist"
                    ) {
                        composable("artist") {
                            ArtistScreen(
                                viewModel = artistViewModel,
                                onAlbumClick = { album ->
                                    navController.navigate("album/${album.idAlbum}")
                                    albumDetailViewModel.loadAlbumDetail(album)
                                }
                            )
                        }

                        composable(
                            route = "album/{albumId}",
                            arguments = listOf(navArgument("albumId") {
                                type = NavType.StringType
                            })
                        ) {
                            val album = albumDetailViewModel.album.value
                            if (album != null) {
                                AlbumDetailScreen(
                                    viewModel = albumDetailViewModel,
                                    album = album
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
