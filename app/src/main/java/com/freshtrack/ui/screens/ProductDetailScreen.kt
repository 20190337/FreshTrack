package com.freshtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.freshtrack.data.local.entities.Product
import com.freshtrack.ui.viewmodel.ProductDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val product by viewModel.getProduct(productId).collectAsState(initial = null)
    var isEditing by remember { mutableStateOf(false) }
    
    var editedName by remember { mutableStateOf("") }
    var editedBrand by remember { mutableStateOf("") }
    var editedQuantity by remember { mutableStateOf("1") }
    var editedExpirationDate by remember { mutableStateOf<Long?>(null) }
    var editedNotificationDays by remember { mutableStateOf(3) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(product) {
        product?.let {
            editedName = it.name
            editedBrand = it.brand ?: ""
            editedQuantity = it.quantity.toString()
            editedExpirationDate = it.expirationDate
            editedNotificationDays = it.notificationDays
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = editedExpirationDate
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { 
                            editedExpirationDate = it 
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Product" else "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        product?.let { currentProduct ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Barcode (read-only)
                OutlinedTextField(
                    value = currentProduct.barcode ?: "N/A",
                    onValueChange = { },
                    label = { Text("Barcode") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isEditing) {
                    // Edit Mode
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Product Name *") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editedBrand,
                        onValueChange = { editedBrand = it },
                        label = { Text("Brand") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editedQuantity,
                        onValueChange = { editedQuantity = it },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            editedExpirationDate?.let { 
                                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                            } ?: "Select Expiration Date"
                        )
                    }

                    // Notification days selector
                    Text("Notify me:", style = MaterialTheme.typography.bodyMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(1, 3, 5, 7).forEach { days ->
                            FilterChip(
                                selected = editedNotificationDays == days,
                                onClick = { editedNotificationDays = days },
                                label = { Text("$days days") }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                viewModel.updateProduct(
                                    currentProduct.copy(
                                        name = editedName,
                                        brand = editedBrand,
                                        quantity = editedQuantity.toIntOrNull() ?: 1,
                                        expirationDate = editedExpirationDate ?: currentProduct.expirationDate,
                                        notificationDays = editedNotificationDays,
                                        updatedAt = System.currentTimeMillis()
                                    )
                                )
                                isEditing = false
                            },
                            modifier = Modifier.weight(1f),
                            enabled = editedName.isNotBlank()
                        ) {
                            Text("Save")
                        }
                    }
                } else {
                    // View Mode
                    OutlinedTextField(
                        value = currentProduct.name,
                        onValueChange = { },
                        label = { Text("Product Name") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = currentProduct.brand ?: "N/A",
                        onValueChange = { },
                        label = { Text("Brand") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = currentProduct.quantity.toString(),
                        onValueChange = { },
                        label = { Text("Quantity") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                            .format(Date(currentProduct.expirationDate)),
                        onValueChange = { },
                        label = { Text("Expiration Date") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "${currentProduct.notificationDays} days before",
                        onValueChange = { },
                        label = { Text("Notification") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Nutritional Facts
                    currentProduct.nutritionalInfo?.let { nutrition ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Nutritional Facts",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(nutrition)
                            }
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
