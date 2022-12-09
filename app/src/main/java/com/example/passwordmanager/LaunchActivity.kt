package com.example.passwordmanager

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.*
import com.example.passwordmanager.dataB.AppDatabase
import com.example.passwordmanager.dataB.MasterPass
import com.example.passwordmanager.dataB.Tables
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        val dbDao = db.masterPassDao()

        setContentView(R.layout.activity_launch)

        val masterPassword = findViewById<View>(R.id.masterPassword) as EditText
        val repeatMasterPassword = findViewById<View>(R.id.repeatMasterPassword) as EditText
        val char8More = findViewById<View>(R.id.charcters8More) as TextView
        val logInButton : Button = findViewById(R.id.logInBitton)


        GlobalScope.launch {
            if (dbDao.checkIfEmpty() == 0){

                logInButton.setOnClickListener {

                    var passMas = masterPassword.text.toString()
                    var repPassMas = repeatMasterPassword.text.toString()

                    GlobalScope.launch {
                    setPassword(passMas, repPassMas, dbDao, masterPassword, repeatMasterPassword)
                    }

                }

            }
            else{
                withContext(Dispatchers.Main){
                    repeatMasterPassword.visibility = View.GONE
                    char8More.visibility = View.GONE

                    logInButton.setOnClickListener {

                        var passMas = masterPassword.text.toString()

                        GlobalScope.launch {
                            checkPass(passMas,dbDao,masterPassword)
                        }
                    }
                }
            }
        }
    }


    fun openMainActivity(pass: String) {
        val intent = Intent(this@LaunchActivity, MainActivity::class.java)
        intent.putExtra("pass", pass)
        startActivity(intent)
    }

    fun sharePassToEdit(pass: String){
        val intent = Intent(this@LaunchActivity, EditPasswordActivity :: class.java)
        intent.putExtra("pass", pass)
    }

    fun sharePassToAdapter(pass: String){
        val intent = Intent(this@LaunchActivity, PassListAdapter :: class.java)
        intent.putExtra("pass", pass)

    }

    //Setting Master Password if empty
    suspend fun setPassword(enteredPass: String,
                            repPass: String,
                            dbdao: MasterPass,
                            masterPassView: EditText,
                            repMasterPassView: EditText)
    {

        when{
            enteredPass.length < 8 -> {
                withContext(Dispatchers.Main) {
                    masterPassView.setText("")
                }
                masterPassView.setHintTextColor(Color.RED)
                masterPassView.setHint(R.string.shortPassword)
            }

            enteredPass != repPass ->{
                withContext(Dispatchers.Main) {
                    repMasterPassView.setText("")
                }
                repMasterPassView.setHintTextColor(Color.RED)
                repMasterPassView.setHint(R.string.wrongPass)
            }

            else -> {
                var mPassEntity = Tables.MasterPassword(AES().encrypt(enteredPass,enteredPass))
                dbdao.insertMasterPass(mPassEntity)

                openMainActivity(enteredPass)

            }
        }
    }

    //comparing Master Password with entered one
    suspend fun checkPass(enteredPass: String,
                          dbdao: MasterPass,
                          masterPassView: EditText)
    {

        if (enteredPass != AES().decrypt(dbdao.getMasterPass(), enteredPass)){
            withContext(Dispatchers.Main) {
                masterPassView.setText("")
            }
            masterPassView.setHintTextColor(Color.RED)
            masterPassView.setHint(R.string.wrongPass)
        }
        else{
            openMainActivity(enteredPass)

        }
    }
}