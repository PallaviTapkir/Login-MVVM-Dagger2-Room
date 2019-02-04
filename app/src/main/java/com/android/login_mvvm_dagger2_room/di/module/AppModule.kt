/*
 * AppModule.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.module

import android.content.Context
import com.android.login_mvvm_dagger2_room.config.AppPreferences
import com.android.login_mvvm_dagger2_room.config.ApplicationClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: ApplicationClass) {

    @Provides
    @Singleton
    fun provideAppPreferences() = AppPreferences.getInstance(application)

    @Provides
    @Singleton
    fun provideApplication(): ApplicationClass = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application
}