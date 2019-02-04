/*
 * Database.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.login_mvvm_dagger2_room.database.dao.DaoClass
import com.android.login_mvvm_dagger2_room.database.entities.User

/**
 * Class for creating database and allow to access data objects of all entities
 */
@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun applicationDao(): DaoClass
}