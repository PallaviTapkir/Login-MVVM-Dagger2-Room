
/*
 * LoginViewModel.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.activity.login

import androidx.lifecycle.LiveData
import androidx.room.EmptyResultSetException
import com.android.login_mvvm_dagger2_room.config.AppPreferences
import com.android.login_mvvm_dagger2_room.di.repository.Repository
import com.android.login_mvvm_dagger2_room.base.BaseViewModel
import com.android.login_mvvm_dagger2_room.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    @Inject
    lateinit var appPref: AppPreferences

    var status = SingleLiveEvent<Status>()

    fun getStatus(): LiveData<Status> {
        return status
    }

    fun loginUser(email: String, password: String) {
        login(email, password)
    }

    /**
     * login
     */
    private fun login(email: String, password: String) {
        try {
            val disposable = repository.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    appPref.saveUserId(user!!.id!!)

                    // set value to live data
                    status.value = LoginSuccess(user)

                }, { throwable ->
                    if(throwable is EmptyResultSetException){
                        status.value = UserNotPresentInDB
                    }else {
                        throwable.printStackTrace()
                        status.value = Failed(throwable.toString())
                    }
                })

            addDisposable(disposable)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}