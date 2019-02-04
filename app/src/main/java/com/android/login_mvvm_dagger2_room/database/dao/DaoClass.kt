/*
 * DaoClass.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.login_mvvm_dagger2_room.database.entities.User
import io.reactivex.Single

/**
 * Dao class access to all methods related to database
 * @Dao in necessary
 */
@Dao
interface DaoClass {

    /**
     * User table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long

    @Query("select * from user where email_address = :email and password = :password")
    fun getUser(email: String, password: String): Single<User?>

}