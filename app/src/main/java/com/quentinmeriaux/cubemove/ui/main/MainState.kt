package com.quentinmeriaux.cubemove.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainState {
    var shouldShowSettingsDialog by mutableStateOf(false)
    var shouldShowHistoryDialog by mutableStateOf(false)
    var squareX by mutableStateOf(0)
    var squareY by mutableStateOf(0)
    var date by mutableStateOf("")
}