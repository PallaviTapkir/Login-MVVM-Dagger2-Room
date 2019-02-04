/*
 * ViewModelModule.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.login_mvvm_dagger2_room.activity.login.LoginViewModel
import com.android.login_mvvm_dagger2_room.activity.signup.SignUpViewModel
import com.android.login_mvvm_dagger2_room.di.repository.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelFactory.ViewModelKey(SignUpViewModel::class)
    fun provideSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel = signUpViewModel

    @Provides
    @IntoMap
    @ViewModelFactory.ViewModelKey(LoginViewModel::class)
    fun providedLoginViewModel(loginViewModel: LoginViewModel): ViewModel = loginViewModel
}