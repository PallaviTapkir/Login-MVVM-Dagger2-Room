/*
 * ActivityComponent.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.di.component

import com.android.login_mvvm_dagger2_room.activity.login.LoginActivity
import com.android.login_mvvm_dagger2_room.activity.signup.SignUpActivity
import com.android.login_mvvm_dagger2_room.di.module.ActivityModule
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(signUpActivity: SignUpActivity)
    fun inject(loginActivity: LoginActivity)

}