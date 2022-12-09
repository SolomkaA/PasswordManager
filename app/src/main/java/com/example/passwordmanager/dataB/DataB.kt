package com.example.passwordmanager.dataB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [
    Tables.MasterPassword :: class,
    Tables.Passwords :: class],
    version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun masterPassDao(): MasterPass
    abstract fun passDao(): DBDao
}

