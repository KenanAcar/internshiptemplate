package com.example.foodorderingapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.foodorderingapp.models.DemoFoodOrderingViewModel
import com.example.foodorderingapp.models.PaymentMethod
import java.text.SimpleDateFormat
import java.util.*

// Receipt Dialog - Traditional Receipt Format
@Composable
fun ReceiptDialog(
    viewModel: DemoFoodOrderingViewModel,
    paymentMethod: PaymentMethod,
    onDismiss: () -> Unit
) {
    val theme = LocalAppTheme.current
    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val receiptNumber = System.currentTimeMillis().toString().takeLast(6)
    val subtotal = viewModel.cartTotal
    val total = subtotal

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            LazyColumn {
                item {
                    // Close button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Receipt Header
                    Text(
                        text = "RECEIPT",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Date and Receipt Number Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ReceiptField("Date:", "$currentDate, $currentTime")
                        ReceiptField("Receipt Number:", receiptNumber)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Amount Received
                    ReceiptField("Amount Received:", "$${String.format("%.2f", total)}")

                    Spacer(modifier = Modifier.height(8.dp))

                    // For the Payment of
                    Text(
                        text = "For the Payment of:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace
                    )

                    // Horizontal line for writing
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Order items on the line
                    Text(
                        text = viewModel.getCartItemsSummary(),
                        fontSize = 11.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Paid by section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Paid by:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )

                        Text(
                            text = "Received by:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Payment Method checkboxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Payment Method: ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Payment method options with checkboxes
                    PaymentMethodCheckbox("Cash", paymentMethod == PaymentMethod.CASH)
                    PaymentMethodCheckbox("Check", false)
                    PaymentMethodCheckbox("Credit Card", paymentMethod == PaymentMethod.CREDIT_CARD)
                    PaymentMethodCheckbox("Sodexo", paymentMethod == PaymentMethod.SODEXO)
                    PaymentMethodCheckbox("Multinet", paymentMethod == PaymentMethod.MULTINET)
                    PaymentMethodCheckbox("Edenred", paymentMethod == PaymentMethod.EDENRED)
                    PaymentMethodCheckbox("Coupon", paymentMethod == PaymentMethod.COUPON)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Check and Credit Card details (if applicable)
                    if (paymentMethod == PaymentMethod.CREDIT_CARD ||
                        paymentMethod == PaymentMethod.SODEXO ||
                        paymentMethod == PaymentMethod.MULTINET ||
                        paymentMethod == PaymentMethod.EDENRED
                    ) {

                        ReceiptField("Check Number:", "___________")
                        ReceiptField("Credit Card Number:", "5168 **** **** ____")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Exp. ___/___",
                                fontSize = 11.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Sec. Code: ____",
                                fontSize = 11.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dotted line separator
                    Text(
                        text = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Order Details Section
                    Text(
                        text = "ORDER DETAILS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Order items list
                items(viewModel.cartItems) { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${cartItem.quantity}x ${cartItem.product.pname}",
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$${String.format("%.2f", cartItem.subtotal)}",
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtotal line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TOTAL:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "$${String.format("%.2f", total)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Thank you message
                    Text(
                        text = "Thank you for your business!\n${theme.companyName}",
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// Helper Composable for Receipt Fields
@Composable
fun ReceiptField(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Monospace
        )

        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Black,
            fontFamily = FontFamily.Monospace
        )
    }
}

// Helper Composable for Payment Method Checkboxes
@Composable
fun PaymentMethodCheckbox(method: String, isChecked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = if (isChecked) "☑" else "☐",
            fontSize = 12.sp,
            color = Color.Black,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = method,
            fontSize = 11.sp,
            color = Color.Black,
            fontFamily = FontFamily.Monospace
        )
    }
}

// Helper Function for Payment Method Names
fun getPaymentMethodName(method: PaymentMethod): String {
    return when (method) {
        PaymentMethod.CREDIT_CARD -> "Credit Card"
        PaymentMethod.CASH -> "Cash"
        PaymentMethod.COUPON -> "Coupon"
        PaymentMethod.SODEXO -> "Sodexo"
        PaymentMethod.MULTINET -> "Multinet"
        PaymentMethod.EDENRED -> "Edenred"
    }
}