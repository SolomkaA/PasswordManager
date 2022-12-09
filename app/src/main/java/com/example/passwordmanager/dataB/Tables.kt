package com.example.passwordmanager.dataB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Tables  {

    @Entity
    data class MasterPassword(
        @ColumnInfo var password: String?
    ){
        @PrimaryKey(autoGenerate = true) var id: Int? = null
    }



    @Entity
    data class Passwords(
        @ColumnInfo(name = "PassName") var passName: String?,
        @ColumnInfo(name = "PassValue") var passValue: String?
    ){
        @PrimaryKey(autoGenerate = true) var id: Int? = null
    }



}