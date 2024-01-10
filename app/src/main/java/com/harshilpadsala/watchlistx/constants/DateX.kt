package com.harshilpadsala.watchlistx.constants

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object DateX {


    @SuppressLint("NewApi")
    private val currentDate: LocalDate = LocalDate.now()

    @SuppressLint("NewApi")
    fun getPastDateTimeStamp(yearsPast: Int): Long {
        val newDate = LocalDate.of(
            currentDate.year - 30,
            currentDate.month,
            currentDate.dayOfMonth,
        )

        Log.i("PrintDate", newDate.toString())
        Log.i("PrintDate", newDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString())



        return newDate.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
    }

    @SuppressLint("NewApi")
    fun getCurrentDateTimeStamp() : Long {
    return currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
}




    fun dateFormat(timeStamp : Long) : String{
       val dateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
       return dateFormat.format(Date(timeStamp))
    }


}