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

    private val montserrat = FontFamily(
       fonts = listOf(
           Font(
            googleFont = GoogleFont("Montserrat"),
            fontProvider = provider,
            weight = FontWeight.SemiBold,
        ),
           Font(
               googleFont = GoogleFont("Montserrat"),
               fontProvider = provider,
               weight = FontWeight.Medium,
           ),
           Font(
               googleFont = GoogleFont("Montserrat"),
               fontProvider = provider,
               weight = FontWeight.Normal,
           ),
       )
    )

    val titleLarge = TextStyle(
        fontFamily = montserrat,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.light
    )

    val titleMedium = TextStyle(
        fontFamily = montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.light
    )

    val titleSmall = TextStyle(
        fontFamily = montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Darkness.light,
    )

    val labelSmall = TextStyle(
        fontFamily = montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.light,
    )

    val labelLarge = TextStyle(
        fontFamily = montserrat,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.rise,
    )

    val labelMedium = TextStyle(
        fontFamily = montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Darkness.light,
    )

    val bodySmall = TextStyle(
        fontFamily = montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = Darkness.grey,
    )

    val bodyMedium = TextStyle(
        fontFamily = montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Darkness.grey,
    )

    val bodyLarge = TextStyle(
        fontFamily = montserrat,
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

