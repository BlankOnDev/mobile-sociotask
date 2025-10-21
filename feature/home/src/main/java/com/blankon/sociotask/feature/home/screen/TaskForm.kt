package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.domain.dashboard.model.PaymentType
import com.blankon.sociotask.core.domain.dashboard.model.SocialPlatform
import com.blankon.sociotask.core.domain.dashboard.model.TaskDraft
import java.time.LocalDate

@Composable
private fun Labeled(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun TaskForm(
    state: TaskDraft,
    submitting: Boolean,
    onChange: ((TaskDraft) -> TaskDraft) -> Unit,
    onSubmit: () -> Unit,
    onClose: () -> Unit
) {
    // --- Validasi ringan di sisi UI (opsional; bisa digantikan pesan dari VM) ---
    val titleError = state.title.isBlank()
    val descError = state.description.isBlank()
    val rewardError = state.rewardAmount <= 0
    val quotaError = state.quota <= 0
    val deadlineError = remember(state.deadline) {
        // Di sini hanya contoh: jika ada value, tetapi < today maka error
        state.deadline?.isBefore(LocalDate.now()) == true
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Buat Task", style = MaterialTheme.typography.titleLarge)

        // Title
        OutlinedTextField(
            value = state.title,
            onValueChange = { newText ->
                onChange { draft -> draft.copy(title = sanitizeTitle(newText)) }
            },
            label = { Text("Judul task*") },
            singleLine = true,
            isError = titleError,
            supportingText = {
                if (titleError) Text("Judul tidak boleh kosong")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Platform
        Labeled("Platform*")
        FlowRow {
            SocialPlatform.entries.forEach { platform ->
                FilterChip(
                    selected = state.platform == platform,
                    onClick = { onChange { it.copy(platform = platform) } },
                    label = { Text(platform.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        // Description
        OutlinedTextField(
            value = state.description,
            onValueChange = { newText ->
                onChange { draft -> draft.copy(description = newText.trimStart()) }
            },
            label = { Text("Deskripsi*") },
            isError = descError,
            supportingText = {
                if (descError) Text("Deskripsi tidak boleh kosong")
            },
            minLines = 2,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Link (optional)
        OutlinedTextField(
            value = state.link.orEmpty(),
            onValueChange = { newText ->
                onChange { draft ->
                    val trimmed = newText.trim()
                    draft.copy(link = trimmed.takeIf { it.isNotBlank() })
                }
            },
            label = { Text("Link (opsional)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Uri
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Reward + PaymentType (chips)
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.rewardAmount.takeIf { it > 0 }?.toString() ?: "",
                onValueChange = { txt ->
                    onChange {
                        it.copy(rewardAmount = txt.filter(Char::isDigit).toIntOrNull() ?: 0)
                    }
                },
                label = { Text("Reward per user (Pts)*") },
                isError = rewardError,
                supportingText = {
                    if (rewardError) Text("Reward harus lebih dari 0")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // Payment type (gunakan chips agar konsisten & sederhana)
        Labeled("Jenis Pembayaran*")
        FlowRow {
            PaymentType.entries.forEach { pt ->
                FilterChip(
                    selected = state.paymentType == pt,
                    onClick = { onChange { it.copy(paymentType = pt) } },
                    label = { Text(pt.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        // Quota + Deadline
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.quota.takeIf { it > 0 }?.toString() ?: "",
                onValueChange = { txt ->
                    onChange {
                        it.copy(quota = txt.filter(Char::isDigit).toIntOrNull() ?: 0)
                    }
                },
                label = { Text("Kuota peserta*") },
                isError = quotaError,
                supportingText = {
                    if (quotaError) Text("Kuota harus lebih dari 0")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f)
            )

            // Deadline (YYYY-MM-DD) – parse aman
            OutlinedTextField(
                value = state.deadline?.toString().orEmpty(),
                onValueChange = { txt ->
                    onChange { draft ->
                        val trimmed = txt.trim()
                        val parsed = runCatching {
                            if (trimmed.isNotEmpty()) LocalDate.parse(trimmed) else null
                        }.getOrNull()
                        draft.copy(deadline = parsed)
                    }
                },
                label = { Text("Deadline (YYYY-MM-DD)") },
                isError = deadlineError,
                supportingText = {
                    if (deadlineError) Text("Tanggal tidak boleh sebelum hari ini")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // Info general invalid
        if (!state.isValid) {
            Text(
                "Form belum valid",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) { Text("Batal") }
            Button(
                onClick = onSubmit,
                enabled = state.isValid && !submitting,
                modifier = Modifier.weight(1f)
            ) {
                if (submitting) CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp)
                else Text("Simpan Task")
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}

// Helpers
@Composable
private fun FlowRow(content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { content() }
}

private fun sanitizeTitle(input: String): String = input.trim().take(80)
