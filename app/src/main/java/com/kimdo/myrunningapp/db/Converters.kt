package com.kimdo.myrunningapp.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream



class Converters {

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray( byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun fromBitmap(bm: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 95, outputStream)
        return outputStream.toByteArray()
    }
}