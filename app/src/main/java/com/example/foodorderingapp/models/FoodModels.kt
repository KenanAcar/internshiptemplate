package com.example.foodorderingapp.models

import androidx.compose.runtime.*

// =====================================================
// DATA MODELS
// =====================================================

data class Category(
    val cid: Int,
    val cname: String
)

data class Product(
    val pid: Int,
    val cid: Int,
    val pname: String,
    val price: Double,
    val description: String = "",
    val imageUrl: String = ""
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1
) {
    val subtotal: Double
        get() = product.price * quantity
}

// =====================================================
// ENUMS
// =====================================================

enum class PaymentState {
    NONE, SELECTING, PROCESSING, SUCCESS, FAILED
}

enum class PaymentMethod {
    CREDIT_CARD, CASH, COUPON, SODEXO, MULTINET, EDENRED,
}

// =====================================================
// MOCK DATA FOR VISUAL DEMO
// =====================================================

object MockData {

    val categories = listOf(
        Category(1, "Hamburgers"),
        Category(2, "Drinks"),
        Category(3, "Extras")
    )

    val products = listOf(
        // Hamburgers
        Product(
            pid = 1,
            cid = 1,
            pname = "Big King Menu",
            price = 285.99,
            description = "Special Price • Big King Burger + Fries",
            imageUrl = "🍔"
        ),
        Product(
            pid = 2,
            cid = 1,
            pname = "Classic Burger",
            price = 12.99,
            description = "Juicy beef patty with lettuce and tomato",
            imageUrl = "🍔"
        ),
        Product(
            pid = 3,
            cid = 1,
            pname = "Chicken Deluxe",
            price = 11.49,
            description = "Grilled chicken with avocado",
            imageUrl = "🍔"
        ),
        Product(
            pid = 4,
            cid = 1,
            pname = "Veggie Burger",
            price = 10.99,
            description = "Plant-based patty with fresh vegetables",
            imageUrl = "🍔"
        ),
        Product(
            pid = 5,
            cid = 1,
            pname = "BBQ Bacon Burger",
            price = 14.99,
            description = "BBQ sauce with crispy bacon",
            imageUrl = "🍔"
        ),
        Product(
            pid = 6,
            cid = 1,
            pname = "Double Cheese Burger",
            price = 13.49,
            description = "Double beef with extra cheese",
            imageUrl = "🍔"
        ),

        // Drinks
        Product(
            pid = 7,
            cid = 2,
            pname = "Coca Cola",
            price = 2.49,
            description = "Classic refreshing cola drink",
            imageUrl = "🥤"
        ),
        Product(
            pid = 8,
            cid = 2,
            pname = "Orange Juice",
            price = 3.99,
            description = "Fresh squeezed orange juice",
            imageUrl = "🧃"
        ),
        Product(
            pid = 9,
            cid = 2,
            pname = "Water",
            price = 1.99,
            description = "Pure spring water",
            imageUrl = "💧"
        ),
        Product(
            pid = 10,
            cid = 2,
            pname = "Coffee",
            price = 4.49,
            description = "Premium roasted coffee",
            imageUrl = "☕"
        ),

        // Extras
        Product(
            pid = 11,
            cid = 3,
            pname = "French Fries",
            price = 4.99,
            description = "Crispy golden french fries",
            imageUrl = "🍟"
        ),
        Product(
            pid = 12,
            cid = 3,
            pname = "Onion Rings",
            price = 5.49,
            description = "Crispy battered onion rings",
            imageUrl = "🧅"
        ),
        Product(
            pid = 13,
            cid = 3,
            pname = "Chicken Nuggets",
            price = 6.99,
            description = "6-piece chicken nuggets",
            imageUrl = "🍗"
        ),
        Product(
            pid = 14,
            cid = 3,
            pname = "Mozzarella Sticks",
            price = 7.49,
            description = "Crispy mozzarella cheese sticks",
            imageUrl = "🧀"
        )
    )

    fun getProductsByCategory(categoryId: Int): List<Product> {
        return products.filter { it.cid == categoryId }
    }

    fun getCategoryName(categoryId: Int): String {
        return categories.find { it.cid == categoryId }?.cname ?: "Unknown"
    }
}

// =====================================================
// VIEW MODEL FOR DEMO
// =====================================================

class DemoFoodOrderingViewModel {

    var products by mutableStateOf(MockData.products)
        private set

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())
        private set

    var showPaymentDialog by mutableStateOf(false)
        private set

    var paymentState by mutableStateOf(PaymentState.NONE)
        private set

    // ADD THIS: Track the selected payment method
    var selectedPaymentMethod by mutableStateOf(PaymentMethod.CREDIT_CARD)
        private set

    val cartTotal: Double
        get() = cartItems.sumOf { it.subtotal }

    val cartItemCount: Int
        get() = cartItems.sumOf { it.quantity }

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.pid == product.pid }
        if (existingItem != null) {
            cartItems = cartItems.map {
                if (it.product.pid == product.pid) {
                    it.copy(quantity = it.quantity + 1)
                } else it
            }
        } else {
            cartItems = cartItems + CartItem(product, 1)
        }
    }

    fun showPayment() {
        showPaymentDialog = true
        paymentState = PaymentState.SELECTING
    }

    fun hidePayment() {
        showPaymentDialog = false
        paymentState = PaymentState.NONE
    }

    // UPDATED: Store the payment method
    fun processPayment(method: PaymentMethod) {
        selectedPaymentMethod = method  // STORE THE PAYMENT METHOD FIRST!
        paymentState = PaymentState.PROCESSING

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            val isSuccess = (1..10).random() <= 8

            if (isSuccess) {
                paymentState = PaymentState.SUCCESS
                // selectedPaymentMethod is now stored and available
            } else {
                paymentState = PaymentState.FAILED
            }
        }, 2000)
    }

    fun retryPayment() {
        paymentState = PaymentState.SELECTING
    }

    fun clearCartAndHidePayment() {
        cartItems = emptyList()  // Clear cart
        hidePayment()            // Close dialog
    }

    fun searchProducts(query: String): List<Product> {
        return if (query.isBlank()) {
            products
        } else {
            products.filter {
                it.pname.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }

    // for the cartpage
    var showCartDialog by mutableStateOf(false)
        private set

    fun showCart() {
        showCartDialog = true
    }

    fun hideCart() {
        showCartDialog = false
    }

    fun removeFromCart(productId: Int) {
        cartItems = cartItems.filter { it.product.pid != productId }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
        } else {
            cartItems = cartItems.map {
                if (it.product.pid == productId) {
                    it.copy(quantity = quantity)
                } else it
            }
        }
    }

    // Helper function to get cart items summary for payment dialog
    fun getCartItemsSummary(): String {
        return cartItems.joinToString(", ") { "${it.product.pname} x${it.quantity}" }
    }
}