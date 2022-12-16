package com.quentinmeriaux.cubemove.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.quentinmeriaux.cubemove.R
import com.quentinmeriaux.cubemove.model.ThemePreference
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsView(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val settingsUiState by viewModel.settingsUiState.collectAsState()
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(R.string.theme),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                SettingsRow(
                    text = stringResource(R.string.system_default),
                    selected = settingsUiState.theme == ThemePreference.SYSTEM_DEFAULT,
                    onClick = {
                        viewModel.updateThemePreference(ThemePreference.SYSTEM_DEFAULT)
                    }
                )
                SettingsRow(
                    text = stringResource(R.string.light_mode),
                    selected = settingsUiState.theme == ThemePreference.LIGHT,
                    onClick = {
                        viewModel.updateThemePreference(ThemePreference.LIGHT)
                    }
                )
                SettingsRow(
                    text = stringResource(R.string.dark_mode),
                    selected = settingsUiState.theme == ThemePreference.DARK,
                    onClick = {
                        viewModel.updateThemePreference(ThemePreference.DARK)
                    }
                )
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.ok),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}

@Composable
fun SettingsRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}