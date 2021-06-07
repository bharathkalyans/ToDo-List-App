package com.bharathkalyans.todolist.data

import androidx.room.TypeConverter

class Converter {


    /*  Room Database takes only primitive types as input.
    In these cases we have to convert them to primitives using TypeConverter Annotation.
    Room DB is intelligent enough that it detects that methods mentioned here are for TypeConverting.

    */


    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }


    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }


}