/*
 * SignUpViewModel.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.activity.signup

import androidx.lifecycle.LiveData
import com.android.login_mvvm_dagger2_room.base.BaseViewModel
import com.android.login_mvvm_dagger2_room.config.AppPreferences
import com.android.login_mvvm_dagger2_room.database.entities.User
import com.android.login_mvvm_dagger2_room.di.repository.Repository
import com.android.login_mvvm_dagger2_room.livedata.Failed
import com.android.login_mvvm_dagger2_room.livedata.SignUpSuccess
import com.android.login_mvvm_dagger2_room.livedata.SingleLiveEvent
import com.android.login_mvvm_dagger2_room.livedata.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    @Inject
    lateinit var appPref: AppPreferences

    var status = SingleLiveEvent<Status>()

    fun getStatus(): LiveData<Status> {
        return status
    }

    fun insertUserToDatabase(user: User) {
        signUp(user)
    }

    /**
     * sign up
     */
    private fun signUp(user: User) {
        try {
            val disposable = repository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ id ->
                    appPref.saveUserId(id)
                    status.value = SignUpSuccess

                }, { throwable ->
                    throwable.printStackTrace()
                    status.value = Failed(throwable.toString())
                })

            addDisposable(disposable)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}