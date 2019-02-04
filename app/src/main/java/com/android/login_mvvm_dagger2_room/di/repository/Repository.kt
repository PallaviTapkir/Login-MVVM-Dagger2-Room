/*
 * Repository.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.repository

import com.android.login_mvvm_dagger2_room.database.entities.User
import javax.inject.Inject

/**
 * Repository with all methods which invokes the method of data source i.e localDataSource or remoteDataSource
 */
class Repository @Inject constructor(private val localRepo: DataSource) : DataSource {

    override fun loginUser(email: String, password: String) = localRepo.loginUser(email, password)

    override fun insertUser(user: User) = localRepo.insertUser(user)
}