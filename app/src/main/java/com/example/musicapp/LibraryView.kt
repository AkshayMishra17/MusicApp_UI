package com.example.musicapp


//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LibraryView(){
    val categories = listOf("Playlist","Artist","Songs","Album","Genre")

    LazyColumn {
        items(categories){category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category, // Make sure `text` is a `String`
                    style = MaterialTheme.typography.h6,
                    fontSize = 30.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow), // Replace with your right arrow drawable
                    contentDescription = "Right Arrow",
                    modifier = Modifier.size(24.dp).clickable {  } // Adjust size as needed
                )
            }
        }

    }
}