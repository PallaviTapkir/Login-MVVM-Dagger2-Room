/*
 * DatabaseModule.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.module

import android.content.Context
import androidx.room.Room
import com.android.login_mvvm_dagger2_room.config.Config
import com.android.login_mvvm_dagger2_room.database.Database
import com.android.login_mvvm_dagger2_room.database.dao.DaoClass
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {

        private const val DATABASE = "database_name"
    }

    @Provides
    @Named(DATABASE)
    fun provideDatabaseName(): String = Config.DATABASE_NAME

    @Provides
    @Singleton
    fun provideDatabase(context: Context, @Named(DATABASE) databaseName: String): Database {
        return Room.databaseBuilder(context, Database::class.java, databaseName)
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providelDao(databaseDao: Database): DaoClass = databaseDao.applicationDao()
}