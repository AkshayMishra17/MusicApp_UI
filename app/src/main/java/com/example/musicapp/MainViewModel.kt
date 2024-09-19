package com.example.musicapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    private val _currrentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.AddAccount)
    val currentScreen: MutableState<Screen>
        get() = _currrentScreen

    fun setCurrentScreen(screen: Screen){
        _currrentScreen.value = screen
    }

}