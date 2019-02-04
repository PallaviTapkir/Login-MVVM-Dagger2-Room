/*
 * Status.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.livedata

import com.android.login_mvvm_dagger2_room.database.entities.User

sealed class Status

//Global
data class Failed(val error: String) : Status()

data class LoginSuccess(val user : User) : Status()
object SignUpSuccess : Status()
object UserNotPresentInDB : Status()
