package com.example.musicapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase

@Composable
fun SongListScreen(onSongClick: (Song) -> Unit) {
    var songList by remember { mutableStateOf(listOf<Song>()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch songs from Firebase
    LaunchedEffect(Unit) {
        fetchSongsFromFirebase { songs ->
            songList = songs
            isLoading = false  // Set loading to false when data is fetched
        }
    }

    if (isLoading) {
        // Display loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Loading spinner
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top=50.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(songList) { song ->
                SongItem(song) {
                    onSongClick(song)  // Handle song click
                }
            }
        }
    }
}


@Composable
fun SongItem(song: Song, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp,)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for song artwork
            Icon(
                painter = painterResource(id = R.drawable.ic_music_player), // Use your music note icon
                contentDescription = "Song Artwork",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray // Change color as needed
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Artist Name", // Replace with actual artist name if available
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}
private fun fetchSongsFromFirebase(onFetchComplete: (List<Song>) -> Unit) {
    val storage = Firebase.storage
    val storageRef = storage.reference.child("music/")  // Your Firebase folder

    // List all files in the "music/" folder
    storageRef.listAll().addOnSuccessListener { result ->
        val songList = mutableListOf<Song>()
        val tasks = result.items.map { storageReference ->
            // Fetch the download URL
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                songList.add(Song(storageReference.name, uri.toString()))
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        }

        // Wait for all tasks to complete
        Tasks.whenAllComplete(tasks).addOnSuccessListener {
            // Call onFetchComplete after all download tasks are completed
            onFetchComplete(songList)
        }
    }.addOnFailureListener { exception ->
        exception.printStackTrace()
    }
}

