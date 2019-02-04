/*
 * DataSource.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.repository

import com.android.login_mvvm_dagger2_room.database.entities.User
import io.reactivex.Single


/**
 * Interface which includes all methods of local and remote
 */
interface DataSource {

    fun insertUser(user: User): Single<Long>
    fun loginUser(email: String, password: String): Single<User?>
}