/*
 * LoginActivity.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.activity.login

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.login_mvvm_dagger2_room.R
import com.android.login_mvvm_dagger2_room.Utility
import com.android.login_mvvm_dagger2_room.activity.signup.SignUpActivity
import com.android.login_mvvm_dagger2_room.base.BaseActivity
import com.android.login_mvvm_dagger2_room.livedata.Failed
import com.android.login_mvvm_dagger2_room.livedata.LoginSuccess
import com.android.login_mvvm_dagger2_room.livedata.Status
import com.android.login_mvvm_dagger2_room.livedata.UserNotPresentInDB
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun getLayoutResource(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)

        // initialize view model by providing repository to view model factory
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        // observing state of variable
        loginViewModel.getStatus().observe(this, Observer {
            handleResponse(it)
        })

        textSignUp.setOnClickListener {
            SignUpActivity.startSignUpActivity(this)
        }

        btnSignIn.setOnClickListener {
            validateUser()
        }
    }


    private fun handleResponse(status: Status?) {
        status?.let { status1 ->
            when (status1) {
                is Failed -> {
                    Toast.makeText(this, "Failed : ${status1.error}", Toast.LENGTH_LONG).show()
                }
                is LoginSuccess -> {
                    Toast.makeText(this, "Logged in as ${status1.user.emailAddress}", Toast.LENGTH_LONG).show()
                }
                is UserNotPresentInDB -> {
                    Toast.makeText(this, "User not present, please sign up before login", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateUser() {
        val sEmail = edtEmailAddress.text.toString()
        val sPassword = edtPsssword.text.toString()

        if (!sEmail.isEmpty()) {
            if (Utility.isValidEmailAddress(sEmail)) {
                if (!sPassword.isEmpty()) {
                    logIn(sEmail, sPassword)
                } else {
                    edtPsssword.error = getString(R.string.error_empty_message)
                    edtPsssword.requestFocus()
                }
            } else {
                edtEmailAddress.error = getString(R.string.error_invalid_email)
                edtEmailAddress.requestFocus()
            }
        } else {
            edtEmailAddress.error = getString(R.string.error_empty_message)
            edtEmailAddress.requestFocus()
        }
    }

    private fun logIn(email: String, password: String) {
        loginViewModel.loginUser(email, password)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
