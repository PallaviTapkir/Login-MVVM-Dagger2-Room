/*
 * LocalDataSource.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.repository

import com.android.login_mvvm_dagger2_room.database.dao.DaoClass
import com.android.login_mvvm_dagger2_room.database.entities.User
import io.reactivex.Single
import javax.inject.Inject

/**
 * Class is responsible for handling and calling all methods from local data source
 */

class LocalDataSource @Inject constructor(private val daoClass: DaoClass) : DataSource {

    override fun loginUser(email: String, password: String): Single<User?> {
        return daoClass.getUser(email, password)
    }

    override fun insertUser(user: User): Single<Long> {
        return Single.fromCallable { daoClass.insertUser(user) }
    }
}