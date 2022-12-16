package com.quentinmeriaux.cubemove.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.quentinmeriaux.cubemove.R
import com.quentinmeriaux.cubemove.model.MovementData

@Composable
fun HistoryView(
    onDismiss: () -> Unit,
    movements: List<MovementData>
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.history),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Divider()
            LazyColumn {
                items(movements) { movement ->
                    MovementRow(movement)
                    Divider()
                }
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
fun MovementRow(movementData: MovementData) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "X = ${movementData.x}, Y = ${movementData.y}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = "Date = ${movementData.date}",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
