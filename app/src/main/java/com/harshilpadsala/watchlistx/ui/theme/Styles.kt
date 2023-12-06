package com.harshilpadsala.watchlistx.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
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
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.grey
    )

    val titleMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.grey
    )

    val titleSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.grey,
    )

    val labelSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.grey,
    )

    val labelLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.grey,
    )

    val labelMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.grey,
    )

    val bodySmall = TextStyle(
        fontFamily = poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = Darkness.grey,
    )

    val bodyMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Darkness.grey,
    )

    val bodyLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        color = Darkness.grey,
    )

    fun TextStyle.toSpanStyle() : SpanStyle = SpanStyle(
        fontFamily = this.fontFamily,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        color = this.color
    )

}

