package com.example.chatapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.util.*

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey @ColumnInfo(name = "id") var id : String,
    @ColumnInfo(name = "sentBy") var sentBy : String,
    @ColumnInfo(name = "sendAt") var sendAt : Date?,
    @ColumnInfo(name = "text") var text : String,
    @ColumnInfo(name = "media_url") var media_url : String,
    @ColumnInfo(name = "state") var state : MessageState,
    @ColumnInfo(name = "conv_uid") var conv_uid : String
    ){
    constructor() : this("","",java.sql.Date(Calendar.getInstance().time.time),"","",MessageState.NONE,"")

    fun mapMessage(objMap : Map<String, Any>){
        this.id = objMap["id"].toString()
        this.sentBy = objMap["sentBy"].toString()
        this.sendAt = (objMap["sendAt"] as Timestamp).toDate()
        this.text = objMap["text"].toString()
        this.media_url = objMap["media_url"].toString()
        this.state = MessageState.fromInt((objMap["state"] as Long).toInt())
        this.conv_uid = objMap["conv_uid"].toString()
    }
}

enum class MessageState(val value : Int) {
    SENDED(1),
    RECEIVED(2),
    READED(3),
    NONE(4);

    companion object {
        fun fromInt(value : Int) = values().first{it.value == value}
    }
}
