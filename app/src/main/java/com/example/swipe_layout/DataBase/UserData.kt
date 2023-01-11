package com.example.project_contact.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "Foidalanuvchilar")
    val user_name:String,
    val user_Phone_number:String,
    val firstAt:String

)
