/*
 * RepositoryModule.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.module

import com.android.login_mvvm_dagger2_room.di.repository.DataSource
import com.android.login_mvvm_dagger2_room.di.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(localDataSource: LocalDataSource): DataSource = localDataSource

}