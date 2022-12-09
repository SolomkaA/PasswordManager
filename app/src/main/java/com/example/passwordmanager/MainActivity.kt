package com.example.passwordmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.passwordmanager.dataB.AppDatabase
import com.example.passwordmanager.dataB.Tables
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),
            AddPassFragment.AddTableListener{

    private lateinit var passRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        val dbDao = db.passDao()

        var passlis: List<Tables.Passwords>



        GlobalScope.launch {
            passlis = dbDao.getAllPasswords()
        }


    }

    override fun onResume() {

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        var passListTable: List<Tables.Passwords> = emptyList<Tables.Passwords>()

        val dbDao = db.passDao()
        val pass = intent.getStringExtra("pass")

        passRecyclerView = findViewById(R.id.recyclerViewMain)
        passRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        GlobalScope.launch {



            var tempListTable = dbDao.getAllPasswords()
            var row: Tables.Passwords
            var res: Tables.Passwords
            for (i in 0 until tempListTable.size){
                row = tempListTable[i]
                res = tempListTable[i]
                res.passName = row.passName
                res.passValue = row.passValue


            }

            passRecyclerView.adapter = PassListAdapter(tempListTable)

        }

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addButton -> addTable()
        }

        return super.onOptionsItemSelected(item)
    }

    fun addTable(){
        val addTabDialog = AddPassFragment()
        addTabDialog.show(supportFragmentManager, "table")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, name: String, passVal: String) {

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        val dbDao = db.passDao()

        val passMas = intent.getStringExtra("pass")

        var passwords = Tables.Passwords(AES().encrypt(name, passMas!!),AES().encrypt(passVal, passMas!!) )
        GlobalScope.launch {
            dbDao.insertPass(passwords)
        }

        Toast.makeText(this, "$name created", Toast.LENGTH_LONG).show()

    }


}