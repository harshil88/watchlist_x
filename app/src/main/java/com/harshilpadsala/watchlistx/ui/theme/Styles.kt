package com.harshilpadsala.watchlistx.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.harshilpadsala.watchlistx.R


object StylesX {

    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val poppins = FontFamily(
        Font(
            googleFont = GoogleFont("Montserrat"),
            fontProvider = provider,
        )
    )

    val titleLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )

    val titleMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )

    val titleSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
    )

    val labelSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )

    val labelLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )

    val labelMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )

    val bodySmall = TextStyle(
        fontFamily = poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray,
    )

    val bodyMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray,
    )

    val bodyLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray,
    )

}

