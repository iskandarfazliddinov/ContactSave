package com.example.project_contact.DataBase

import androidx.room.*

@Dao
interface UserDao {

    @Query("select * from Users")
    fun getAllUser(): List<UserData>

    @Insert
    fun addUser(userData: UserData)

    @Delete
    fun deletUser(userData: UserData)

    @Update
    fun editUser(userData: UserData)

    @Query("select *from Users where id=:id")
    fun getUserById(id: Long): UserData
}