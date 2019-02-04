/*
 * ApplicationClass.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.config

import android.app.Application
import com.android.login_mvvm_dagger2_room.di.component.AppComponent
import com.android.login_mvvm_dagger2_room.di.component.DaggerAppComponent
import com.android.login_mvvm_dagger2_room.di.module.AppModule
import com.android.login_mvvm_dagger2_room.di.module.DatabaseModule
import com.android.login_mvvm_dagger2_room.di.module.RepositoryModule
import com.android.login_mvvm_dagger2_room.di.module.ViewModelModule

/**
 * Application class for Accessing global declared objects and methods.
 * And class is primarily used for initialization of global state before the
 * first Activity is displayed.
 */

class ApplicationClass : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AppPreferences.getInstance(this)

        buildComponent()
    }

    private fun buildComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .repositoryModule(RepositoryModule())
            .viewModelModule(ViewModelModule())
            .build()
    }
}