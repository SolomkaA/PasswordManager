package com.example.passwordmanager.dataB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBDao {

    @Insert
    suspend fun insertPass(pass: Tables.Passwords)

    @Query("SELECT * FROM Passwords")
    suspend fun getAllPasswords(): List<Tables.Passwords>

    @Query("SELECT * FROM Passwords WHERE id = :id")
    suspend fun getPassword(id: Int): Tables.Passwords

    @Query("UPDATE Passwords SET PassValue = :newPass WHERE id =:id")
    suspend fun updatePass(newPass: String, id: Int)

    @Query("DELETE FROM Passwords WHERE id = :id")
    suspend fun deletePass(id: Int)
}

@Dao
interface MasterPass{
    @Query("SELECT count(*) FROM MasterPassword")
    suspend fun checkIfEmpty(): Int

    @Insert
    suspend fun insertMasterPass(mPass: Tables.MasterPassword)

    @Query("SELECT password FROM MasterPassword WHERE id = 1")
    suspend fun getMasterPass(): String



    @Query("UPDATE MasterPassword SET password = :newPass WHERE id = :id")
    suspend fun updatePass(id: Int, newPass: String)
}