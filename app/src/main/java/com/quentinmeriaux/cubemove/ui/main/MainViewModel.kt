package com.quentinmeriaux.cubemove.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quentinmeriaux.cubemove.data.MovementDataDao
import com.quentinmeriaux.cubemove.model.MovementData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val movementDataDao: MovementDataDao) : ViewModel() {

    val movements: StateFlow<List<MovementData>> =
        movementDataDao.getAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf()
            )

    fun addMovement(x: Int, y: Int, date: String) {
        viewModelScope.launch {
            movementDataDao.add(MovementData(x, y, date))
        }
    }

    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.FRANCE)
        return formatter.format(calendar.time)
    }
}