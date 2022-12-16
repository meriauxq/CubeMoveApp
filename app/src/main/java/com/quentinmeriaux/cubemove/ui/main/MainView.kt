package com.quentinmeriaux.cubemove.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.quentinmeriaux.cubemove.R
import com.quentinmeriaux.cubemove.ui.history.HistoryView
import com.quentinmeriaux.cubemove.ui.settings.SettingsView
import com.quentinmeriaux.cubemove.ui.theme.yellow
import org.koin.androidx.compose.koinViewModel
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    mainState: MainState = remember { MainState() },
    viewModel: MainViewModel = koinViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                title = {
                    Text(text = stringResource(R.string.top_app_bar_title))
                },
                actions = {
                    IconButton(onClick = { mainState.shouldShowHistoryDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = stringResource(R.string.history)
                        )
                    }
                    IconButton(onClick = { mainState.shouldShowSettingsDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->

        if (mainState.shouldShowSettingsDialog) {
            SettingsView(
                onDismiss = { mainState.shouldShowSettingsDialog = false }
            )
        }

        val movements by viewModel.movements.collectAsState()

        if (mainState.shouldShowHistoryDialog) {
            HistoryView(
                onDismiss = { mainState.shouldShowHistoryDialog = false },
                movements = movements
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }

            var squareSize by remember { mutableStateOf(0f) }

            Box(
                Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .background(yellow)
                    .size(dimensionResource(id = R.dimen.square_size))
                    .onGloballyPositioned { coordinates ->
                        squareSize = coordinates.size.height.toFloat()
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            },
                            onDragEnd = {
                                mainState.squareX = (offsetX + squareSize / 2).roundToInt()
                                mainState.squareY = (offsetY + squareSize / 2).roundToInt()
                                mainState.date = viewModel.getFormattedDate()
                                viewModel.addMovement(
                                    mainState.squareX,
                                    mainState.squareY,
                                    mainState.date
                                )
                            }
                        )
                    }
            )

            MovementData(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                x = mainState.squareX,
                y = mainState.squareY,
                date = mainState.date
            )
        }
    }
}

@Composable
fun MovementData(modifier: Modifier = Modifier, x: Int = 0, y: Int = 0, date: String) {
    Box(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        Text(
            text = stringResource(id = R.string.movement_data, x, y, date),

            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                .padding(8.dp),
            style = TextStyle(color = MaterialTheme.colorScheme.surface)
        )
    }
}