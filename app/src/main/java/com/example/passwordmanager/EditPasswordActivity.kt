package com.example.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.passwordmanager.dataB.AppDatabase

class EditPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        val id = intent.getStringExtra("id")

        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        val editPass = findViewById<View>(R.id.editPassText) as EditText



    }
}