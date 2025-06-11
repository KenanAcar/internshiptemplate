package com.example.foodorderingapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.foodorderingapp.models.DemoFoodOrderingViewModel
import com.example.foodorderingapp.models.PaymentMethod

@Composable
fun PaymentMethodDialog(viewModel: DemoFoodOrderingViewModel) {
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CREDIT_CARD) }

    Dialog(onDismissRequest = { viewModel.hidePayment() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFF8C42))
                .padding(20.dp)
        ) {
            Column {
                // Header
                Text(
                    text = "Payment Method",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Order: ${viewModel.getCartItemsSummary()}",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Text(
                    text = "Price: ${String.format("%.2f", viewModel.cartTotal)} TL",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Payment Methods - Now selectable
                PaymentMethodOption(
                    icon = Icons.Default.CreditCard,
                    text = "5168 **** Credit Card",
                    isSelected = selectedPaymentMethod == PaymentMethod.CREDIT_CARD,
                    onClick = { selectedPaymentMethod = PaymentMethod.CREDIT_CARD }
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    icon = Icons.Default.Money,
                    text = "Cash",
                    isSelected = selectedPaymentMethod == PaymentMethod.CASH,
                    onClick = { selectedPaymentMethod = PaymentMethod.CASH }
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    icon = Icons.Default.LocalOffer,
                    text = "Coupon",
                    isSelected = selectedPaymentMethod == PaymentMethod.COUPON,
                    onClick = { selectedPaymentMethod = PaymentMethod.COUPON }
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    icon = Icons.Default.CreditCard,
                    text = "Sodexo",
                    isSelected = selectedPaymentMethod == PaymentMethod.SODEXO,
                    onClick = { selectedPaymentMethod = PaymentMethod.SODEXO }
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    icon = Icons.Default.CreditCard,
                    text = "Multinet",
                    isSelected = selectedPaymentMethod == PaymentMethod.MULTINET,
                    onClick = { selectedPaymentMethod = PaymentMethod.MULTINET }
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    icon = Icons.Default.CreditCard,
                    text = "Edenred",
                    isSelected = selectedPaymentMethod == PaymentMethod.EDENRED,
                    onClick = { selectedPaymentMethod = PaymentMethod.EDENRED }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Pay Now Button
                Button(
                    onClick = { viewModel.processPayment(selectedPaymentMethod) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFFF8C42)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Pay Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentMethodOption(
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White,
                unselectedColor = Color.White
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun PaymentProcessingDialog() {
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFF8C42))
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Processing Payment...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PaymentFailedDialog(viewModel: DemoFoodOrderingViewModel) {
    Dialog(onDismissRequest = { viewModel.hidePayment() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFF8C42))
                .padding(20.dp)
        ) {
            Column {
                // Header
                Text(
                    text = "Payment Method",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Order: ${viewModel.getCartItemsSummary()}",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Text(
                    text = "Price: ${String.format("%.2f", viewModel.cartTotal)} TL",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Failed Payment Dialog
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Warning Icon
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "!",
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Failed Payment Try Again",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { viewModel.retryPayment() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF8C42)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "OK",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentSuccessDialog(viewModel: DemoFoodOrderingViewModel) {
    Dialog(onDismissRequest = { viewModel.hidePayment() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFF8C42))
                .padding(20.dp)
        ) {
            Column {
                // Header
                Text(
                    text = "Payment Method",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Order: ${viewModel.getCartItemsSummary()}",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Text(
                    text = "Price: ${String.format("%.2f", viewModel.cartTotal)} TL",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Success message area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Payment Successful",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Success",
                                tint = Color.Green,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "You can go back to main page",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.clearCartAndHidePayment()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFFF8C42)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home",
                                    tint = Color(0xFFFF8C42),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Back to Home",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}