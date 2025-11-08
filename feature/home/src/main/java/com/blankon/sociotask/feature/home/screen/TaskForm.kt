package com.blankon.sociotask.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.domain.model.PaymentType
import com.blankon.sociotask.core.domain.model.SocialPlatform
import com.blankon.sociotask.core.domain.model.TaskDraft
import java.time.LocalDate

@Composable
private fun Labeled(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    // State untuk melacak apakah error harus ditampilkan
    var showErrors by rememberSaveable { mutableStateOf(false) }

    // Logika validasi tetap di sini
    val titleError = state.title.isBlank()
    val descError = state.description.isBlank()
    val rewardError = state.rewardAmount <= 0
    val quotaError = state.quota <= 0
    val deadlineError = remember(state.deadline) {
        state.deadline?.isBefore(LocalDate.now()) == true
    }

    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // Penting agar tidak tertutup navigation bar
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                // Gunakan contentPadding, bukan padding biasa, untuk LazyColumn
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Text("Buat Task", style = MaterialTheme.typography.titleMedium)
            }

            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { newText ->
                        onChange { draft -> draft.copy(title = sanitizeTitle(newText)) }
                    },
                    label = { Text("Judul task*") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Notes,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    // Tampilkan error HANYA JIKA showErrors true
                    isError = titleError && showErrors,
                    supportingText = {
                        if (titleError && showErrors) Text("Judul tidak boleh kosong")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Labeled("Platform*")
            }

            item {
                FlowRow {
                    SocialPlatform.entries.forEach { platform ->
                        FilterChip(
                            selected = state.platform == platform,
                            onClick = { onChange { it.copy(platform = platform) } },
                            label = { Text(platform.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.12f
                                ),
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { newText ->
                        onChange { draft -> draft.copy(description = newText.trimStart()) }
                    },
                    label = { Text("Deskripsi*") },
                    // Tampilkan error HANYA JIKA showErrors true
                    isError = descError && showErrors,
                    supportingText = {
                        if (descError && showErrors) Text("Deskripsi tidak boleh kosong")
                    },
                    minLines = 3,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = state.link.orEmpty(),
                    onValueChange = { newText ->
                        onChange { draft ->
                            val trimmed = newText.trim()
                            draft.copy(link = trimmed.takeIf { it.isNotBlank() })
                        }
                    },
                    label = { Text("Link (opsional)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Link,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Uri
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Reward & kuota",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
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
                        // Tampilkan error HANYA JIKA showErrors true
                        isError = rewardError && showErrors,
                        supportingText = {
                            if (rewardError && showErrors) Text("Reward harus lebih dari 0")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Labeled("Jenis Pembayaran*")
            }

            item {
                FlowRow {
                    PaymentType.entries.forEach { pt ->
                        FilterChip(
                            selected = state.paymentType == pt,
                            onClick = { onChange { it.copy(paymentType = pt) } },
                            label = { Text(pt.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.12f
                                ),
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            item {
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
                        // Tampilkan error HANYA JIKA showErrors true
                        isError = quotaError && showErrors,
                        supportingText = {
                            if (quotaError && showErrors) Text("Kuota harus lebih dari 0")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )

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
                        label = { Text("Deadline") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Event,
                                contentDescription = null
                            )
                        },
                        // Tampilkan error HANYA JIKA showErrors true
                        isError = deadlineError && showErrors,
                        supportingText = {
                            if (deadlineError && showErrors) Text("Tanggal tidak boleh sebelum hari ini")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                // Tampilkan pesan error global HANYA JIKA showErrors true
                if (!state.isValid && showErrors) {
                    Text(
                        "Form belum valid, periksa kembali field di atas.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.weight(1f)
                    ) { Text("Batal") }

                    Button(
                        onClick = {
                            // 1. Set showErrors jadi true saat tombol ditekan
                            showErrors = true
                            // 2. Hanya jalankan onSubmit jika state valid
                            if (state.isValid) {
                                onSubmit()
                            }
                        },
                        // Tombol selalu aktif kecuali saat sedang submitting
                        enabled = !submitting,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (submitting)
                            CircularProgressIndicator(
                                Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        else
                            Text("Simpan Task")
                    }
                }
            }

            // Spacer di akhir agar tombol tidak menempel di bawah
            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}


@Composable
private fun FlowRow(content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { content() }
}

private fun sanitizeTitle(input: String): String = input.trim().take(80)


@Preview
@Composable
private fun TaskFormPreview() {
    SociotaskTheme {
        TaskForm(
            state = TaskDraft(),
            submitting = false,
            onChange = {},
            onSubmit = {},
            onClose = {}
        )
    }
}