package com.example.musicapp

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AccountView(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
          Row(){
              Icon(imageVector = Icons.Default.AccountCircle,
                  contentDescription ="Account",
                  modifier = Modifier.padding(end = 8.dp)
                  )
              Column {
                  Text(text = "Akshay Tutorials")
                  Text(text = "AK@Tutorials")

              }
          }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_music_player), contentDescription = "My Music",
            modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "My Music")
        }
        Divider() //horixontal line
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize() // Fills the parent container
                .wrapContentSize(Alignment.Center) // Centers the content
        ) {
            Image(
                painter = painterResource(id = R.drawable.musicui_photo),
                contentDescription = "Centered Image",
                modifier = Modifier.size(400.dp).border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp)).clickable {
                    Toast.makeText(context,"Music App",Toast.LENGTH_SHORT).show()
                    },
                contentScale = ContentScale.Crop,

            )
        }
    }
}