package com.bharathkalyans.todolist.data.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//This will be our Model Class which will hold data!
//Parcelize is used for Safe Args to pass data b/w Fragments!
@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable