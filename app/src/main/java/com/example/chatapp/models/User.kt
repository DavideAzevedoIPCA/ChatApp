package com.example.chatapp.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    @PrimaryKey @ColumnInfo(name = "uid") var uid: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "photo_url") var photo_url: String
    ) {
    constructor() : this("","","","")
}