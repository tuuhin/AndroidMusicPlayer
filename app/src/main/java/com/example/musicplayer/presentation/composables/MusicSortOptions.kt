package com.example.musicplayer.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.musicplayer.presentation.util.MusicSortOrder
import com.example.musicplayer.presentation.util.states.MusicSortState

@Composable
fun MusicSortOptions(
    sortState: MusicSortState,
    onSortOrderChange: (MusicSortOrder) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    dialogColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    radioSelectedColor: Color = MaterialTheme.colorScheme.primary,
    radioUnselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    if (sortState.isDialogOpen)
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = modifier,
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = dialogColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Pick Sort Order",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    MusicSortOrder.values()
                        .forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable { onSortOrderChange(option) }
                            ) {
                                RadioButton(
                                    selected = sortState.sortOrder == option,
                                    onClick = { onSortOrderChange(option) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = radioSelectedColor,
                                        unselectedColor = radioUnselectedColor
                                    )
                                )
                                Text(
                                    text = option.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = textColor
                                )
                            }
                        }
                }
            }
        }
}

@Preview
@Composable
fun MusicSortOptionsPreview() {
    MusicSortOptions(
        sortState = MusicSortState(isDialogOpen = true),
        onSortOrderChange = {}
    )
}