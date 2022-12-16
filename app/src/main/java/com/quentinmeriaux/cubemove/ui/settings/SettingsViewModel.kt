package com.quentinmeriaux.cubemove.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quentinmeriaux.cubemove.model.ThemePreference
import com.quentinmeriaux.cubemove.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: UserDataRepository) : ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        repository.userData
            .map { SettingsUiState(theme = it.themePreference) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState(ThemePreference.SYSTEM_DEFAULT)
            )


    fun updateThemePreference(themePreference: ThemePreference) {
        viewModelScope.launch {
            repository.setThemePreference(themePreference)
        }
    }
}
