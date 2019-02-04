/*
 * AppComponent.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.component

import com.android.login_mvvm_dagger2_room.di.module.*
import com.android.login_mvvm_dagger2_room.di.repository.Repository
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [DatabaseModule::class,
        AppModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)

@Singleton
interface AppComponent {
    fun activityModule(activityModule: ActivityModule): ActivityComponent

    fun provideRepository(): Repository

}