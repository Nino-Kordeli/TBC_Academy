package com.example.tbcacademy.feature.register.screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tbcacademy.domain.model.Field
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.screen.register.vm.RegisterViewModel
import com.example.tbcacademy.presentation.theme.Black
import com.example.tbcacademy.presentation.theme.Blue
import com.example.tbcacademy.presentation.theme.GradientEnd
import com.example.tbcacademy.presentation.theme.GradientStart
import com.example.tbcacademy.presentation.theme.Gray
import com.example.tbcacademy.presentation.theme.Red
import com.example.tbcacademy.presentation.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen(
    navigator: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            RegisterHeader()

            Spacer(modifier = Modifier.height(40.dp))

            when (val currentState = state) {
                is Resource.Loader -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Blue)
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = "Error loading fields: ${currentState.message}",
                        color = Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is Resource.Success -> {
                    val fields = currentState.data

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(8.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(fields) { field ->
                                FieldItem(
                                    field = field,
                                    onValueChange = { viewModel.updateValue(field.id, it) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val validationResult = validateFields(fields)
                            if (validationResult.isValid) {
                                Toast.makeText(
                                    context,
                                    "Registration successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    validationResult.errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(55.dp)
                            .align(Alignment.End),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(GradientStart, GradientEnd)
                                    ),
                                    shape = RoundedCornerShape(28.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Register",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Logo",
                modifier = Modifier.size(45.dp),
                tint = Blue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "E-Auth",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
        }

        Text(
            text = "Register",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Black,
            modifier = Modifier.padding(end = 20.dp)
        )
    }
}

@Composable
fun FieldItem(
    field: Field,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            when (field.type) {
                "input" -> {
                    InputField(field = field, onValueChange = onValueChange)
                }

                "chooser" -> {
                    when {
                        field.hint.contains("Birthday", ignoreCase = true) -> {
                            DatePickerField(field = field, onValueChange = onValueChange)
                        }

                        field.hint.contains("Gender", ignoreCase = true) -> {
                            GenderDropdownField(field = field, onValueChange = onValueChange)
                        }

                        else -> {
                            InputField(field = field, onValueChange = onValueChange)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputField(
    field: Field,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = field.value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = if (field.required) "${field.hint} *" else field.hint,
                color = if (field.required && field.value.isBlank()) Red else Gray
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (field.keyboard) {
                "number" -> KeyboardType.Number
                "email" -> KeyboardType.Email
                "phone" -> KeyboardType.Phone
                else -> KeyboardType.Text
            }
        ),
        isError = field.required && field.value.isBlank(),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "Field logo",
                tint = Blue,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (field.required && field.value.isBlank()) Red else Blue,
            unfocusedBorderColor = if (field.required && field.value.isBlank()) Red else Gray,
            focusedLabelColor = Blue,
            cursorColor = Blue
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    field: Field,
    onValueChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    OutlinedTextField(
        value = field.value,
        onValueChange = {},
        label = {
            Text(
                text = if (field.required) "${field.hint} *" else field.hint,
                color = if (field.required && field.value.isBlank()) Red else Gray
            )
        },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date",
                    tint = Blue
                )
            }
        },
        isError = field.required && field.value.isBlank(),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (field.required && field.value.isBlank())
                Red
            else
                Blue,
            unfocusedBorderColor = if (field.required && field.value.isBlank())
                Red
            else
                Gray,
            disabledBorderColor = Gray
        )
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Date(millis)
                            onValueChange(dateFormatter.format(date))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", color = Blue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun GenderDropdownField(
    field: Field,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other", "Prefer not to say")

    OutlinedTextField(
        value = field.value,
        onValueChange = {},
        label = {
            Text(
                text = if (field.required) "${field.hint} *" else field.hint,
                color = if (field.required && field.value.isBlank()) Red else Gray
            )
        },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select gender",
                    tint = Blue
                )
            }
        },
        isError = field.required && field.value.isBlank(),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (field.required && field.value.isBlank())
                Red
            else
                Blue,
            unfocusedBorderColor = if (field.required && field.value.isBlank())
                Red
            else
                Gray,
            disabledBorderColor = Gray
        )
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        genderOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    onValueChange(option)
                    expanded = false
                }
            )
        }
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

fun validateFields(fields: List<Field>): ValidationResult {
    fields.forEach { field ->
        if (field.required && field.value.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "${field.hint} is required"
            )
        }

        if (field.value.isNotBlank()) {
            when {
                field.hint.contains("Email", ignoreCase = true) -> {
                    if (!isValidEmail(field.value)) {
                        return ValidationResult(
                            isValid = false,
                            errorMessage = "Please enter a valid email"
                        )
                    }
                }

                field.hint.contains("Phone", ignoreCase = true) -> {
                    if (!isValidPhone(field.value)) {
                        return ValidationResult(
                            isValid = false,
                            errorMessage = "Please enter a valid phone number"
                        )
                    }
                }
            }
        }
    }

    return ValidationResult(isValid = true)
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhone(phone: String): Boolean {
    return phone.length >= 9 && phone.all { it.isDigit() }
}