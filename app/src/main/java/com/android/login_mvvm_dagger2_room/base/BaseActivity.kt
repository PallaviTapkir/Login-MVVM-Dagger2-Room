/*
 * BaseActivity.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.login_mvvm_dagger2_room.config.ApplicationClass
import com.android.login_mvvm_dagger2_room.di.component.ActivityComponent
import com.android.login_mvvm_dagger2_room.di.module.ActivityModule
import io.reactivex.disposables.CompositeDisposable


/**
 * Abstract class with all common functions.
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    lateinit var activityComponent: ActivityComponent

    var progressDialog: ProgressDialog? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())

        activityComponent = (application as ApplicationClass).appComponent
            .activityModule(ActivityModule(this))

    }

    /**
     * Returns layout id
     * */
    abstract fun getLayoutResource(): Int

    /**
     * Shows progress dialog
     * @message Message display on progress dialog
     */
    fun showProgressDialog(message: String) {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage(message)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }

    /**
     * Dismiss progress dialog
     */
    fun dismissProgressDialog() {
        progressDialog!!.dismiss()
    }

    /**
     * Unregister RxBus and clear composite compositeDisposable for avoiding memory leaks
     */
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}