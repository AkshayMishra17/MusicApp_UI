package com.example.musicapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.musicapp.ui.theme.MusicAppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var auth: FirebaseAuth

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                   MainView()
                }
            }
        }
    }

    private fun playSong(song: Song) {
        // Release any previous MediaPlayer instance
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(song.url)  // Set the data source to the song URL
            prepareAsync()  // Prepare the media player asynchronously
            setOnPreparedListener {
                start()  // Start playing when prepared
            }
            setOnErrorListener { mp, _, _ ->
                // Handle errors during playback
                mp.reset() // Reset the MediaPlayer on error
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()  // Release the MediaPlayer when the activity is destroyed
        mediaPlayer = null
    }
}
