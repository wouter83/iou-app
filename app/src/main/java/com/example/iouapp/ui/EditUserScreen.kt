package com.example.iouapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.iouapp.R
import com.example.iouapp.data.User
import com.example.iouapp.ui.theme.NegativeRed
import com.example.iouapp.ui.theme.PositiveGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    user: User?,
    onNavigateBack: () -> Unit,
    onSave: (User, String) -> Boolean,
    onAddAmount: (User, Double) -> Unit,
    onSubtractAmount: (User, Double) -> Unit,
    onDelete: (User) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }

    LaunchedEffect(user?.id) {
        name = user?.name.orEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.edit_user)) })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        if (user == null) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.user_name_hint)) },
                    singleLine = true
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.amount_hint)) },
                    singleLine = true
                )

                // Amount actions
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            val parsed = amountText.replace(',', '.').toDoubleOrNull()
                            if (parsed == null || parsed == 0.0) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(message = context.getString(R.string.amount_required))
                                }
                            } else if (user != null) {
                                onAddAmount(user, parsed)
                                amountText = ""
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = PositiveGreen, contentColor = androidx.compose.ui.graphics.Color.White)
                    ) {
                        Text(text = stringResource(id = R.string.add))
                    }

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            val parsed = amountText.replace(',', '.').toDoubleOrNull()
                            if (parsed == null || parsed == 0.0) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(message = context.getString(R.string.amount_required))
                                }
                            } else if (user != null) {
                                onSubtractAmount(user, parsed)
                                amountText = ""
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NegativeRed, contentColor = androidx.compose.ui.graphics.Color.White)
                    ) {
                        Text(text = stringResource(id = R.string.subtract))
                    }
                }

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        val success = onSave(user, name)
                        if (success) {
                            onNavigateBack()
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(message = context.getString(R.string.name_required))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.save))
                }

                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }

                Button(
                    onClick = {
                        user?.let {
                            onDelete(it)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = NegativeRed, contentColor = androidx.compose.ui.graphics.Color.White)
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            }
        }
    }
}
