package com.example.musicapp

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable



@Composable
fun BrowseView() {
    val categories = listOf("Hits", "Happy", "Workout", "Running", "TGIF", "Yoga")

    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(categories){ cat ->
            BrowserItem(cat = cat, drawable = R.drawable.ic_library)
        }
    }

}


