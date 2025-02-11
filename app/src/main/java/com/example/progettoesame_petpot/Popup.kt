package com.example.progettoesame_petpot

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun AutoDismissPopup(
    message: String,
    onDismiss: () -> Unit,
    durationMillis: Long = 2000, // 🕒 Tempo di auto-chiusura (2 sec di default)
    showButtons: Boolean = false,
    onConfirm: (() -> Unit)? = null,
    confirmText: String = "Conferma",
    dismissText: String = "Chiudi"
) {
    // 🔥 Avvia un timer per chiudere automaticamente il popup
    LaunchedEffect(Unit) {
        delay(durationMillis)
        onDismiss()
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Message") },
        text = { Text(message) },
        confirmButton = {
            if (showButtons && onConfirm != null) {
                Button(onClick = { onConfirm() }) {
                    Text(confirmText)
                }
            }
        },
        dismissButton = {
            if (showButtons) {
                Button(onClick = { onDismiss() }) {
                    Text(dismissText)
                }
            }
        }
    )
}

