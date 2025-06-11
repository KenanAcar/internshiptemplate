package com.example.foodorderingapp.ui.theme
import com.example.foodorderingapp.BuildConfig
import androidx.compose.ui.graphics.Color
object ThemeManager {

    data class AppTheme(
        val primaryColor: Color,
        val backgroundColor: Color,
        val cardColor: Color,
        val successColor: Color,
        val textOnPrimary: Color = Color.White,
        val companyName: String
    )

    private val orangeTheme = AppTheme(
        primaryColor = Color(0xFFFF8C42),
        backgroundColor = Color(0xFFFFC680),
        cardColor = Color(0xFFFF8C42),
        successColor = Color(0xFF4CAF50),
        companyName = "Company A"
    )

    private val greenTheme = AppTheme(
        primaryColor = Color(0xFF4CAF50),
        backgroundColor = Color(0xFFC8E6C9),
        cardColor = Color(0xFF66BB6A),
        successColor = Color(0xFF2E7D32),
        companyName = "Company B"
    )

    private val purpleTheme = AppTheme(
        primaryColor = Color(0xFF8958B9),
        backgroundColor = Color(0xA9D0B5FF),
        cardColor = Color(0xFFB161E4),
        successColor = Color(0xFF411C81),
        companyName = "Company C"
    )

    fun getCurrentTheme(): AppTheme {
        return when (BuildConfig.COMPANY_THEME) {
            "ORANGE" -> orangeTheme
            "GREEN" -> greenTheme
            "PURPLE"-> purpleTheme
            else -> orangeTheme // Default fallback
        }
    }
}