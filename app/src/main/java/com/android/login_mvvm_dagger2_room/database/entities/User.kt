/*
 * User.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Data class used to store user's data
 */
@Entity(tableName = "user")
class User : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "name")
    var userName: String? = null

    @ColumnInfo(name = "email_address")
    var emailAddress: String? = null

    @ColumnInfo(name = "password")
    var password: String? = null

}
