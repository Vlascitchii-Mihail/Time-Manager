package com.vm.timemanager.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    //converts Data to String
    @TypeConverter fun fromDate(date: LocalDateTime?) : String? {
        return date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    }

    //converts String to Data
    @TypeConverter fun toDate(date: String?): LocalDateTime? {
        return date?.let {
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        }
    }

}