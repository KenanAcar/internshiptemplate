package com.example.foodorderingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.foodorderingapp.models.DemoFoodOrderingViewModel
import com.example.foodorderingapp.models.PaymentState
import com.example.foodorderingapp.ui.theme.MainScrollablePage
import com.example.foodorderingapp.ui.theme.PaymentMethodDialog
import com.example.foodorderingapp.ui.theme.PaymentProcessingDialog
import com.example.foodorderingapp.ui.theme.PaymentSuccessDialog
import com.example.foodorderingapp.ui.theme.PaymentFailedDialog
import com.example.foodorderingapp.ui.theme.FoodOrderingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodOrderingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FoodOrderingApp()
                }
            }
        }
    }
}

@Composable
fun FoodOrderingApp() {
    val viewModel = remember { DemoFoodOrderingViewModel() }

    // Main Screen
    MainScrollablePage(viewModel = viewModel)

    // Payment Dialogs
    if (viewModel.showPaymentDialog) {
        when (viewModel.paymentState) {
            PaymentState.SELECTING -> PaymentMethodDialog(viewModel)
            PaymentState.PROCESSING -> PaymentProcessingDialog()
            PaymentState.SUCCESS -> PaymentSuccessDialog(viewModel)
            PaymentState.FAILED -> PaymentFailedDialog(viewModel)
            PaymentState.NONE -> Unit
        }
    }
}