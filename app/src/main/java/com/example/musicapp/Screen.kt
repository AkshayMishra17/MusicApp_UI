package com.example.musicapp

import androidx.annotation.DrawableRes

sealed class Screen(val title:String,val route:String) {
    sealed class BottomScreen(
        val bTitle:String,val broute:String,@DrawableRes val icon:Int):Screen(bTitle,broute){
        object Home : BottomScreen("Home","home",R.drawable.ic_music_player)
        object Library: BottomScreen("Library","library",R.drawable.ic_library)
        object Browse: BottomScreen("Browse","browse",R.drawable.ic_browse)
    }
    sealed class DrawerScreen(val dTitle:String,val droute:String,@DrawableRes val icon:Int):Screen(dTitle,droute){
        object Account:DrawerScreen("Account","account",R.drawable.ic_account)
        object Subscription:DrawerScreen("Subscription","subscribe",R.drawable.ic_subscribe)
        object AddAccount:DrawerScreen("Add Account","add_account",R.drawable.baseline_person_add_alt_1_24)
        object SearchBar:DrawerScreen("Song List","song_list",R.drawable.ic_music_player)

    }
}

val screenInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)
val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount,
    Screen.DrawerScreen.SearchBar

)