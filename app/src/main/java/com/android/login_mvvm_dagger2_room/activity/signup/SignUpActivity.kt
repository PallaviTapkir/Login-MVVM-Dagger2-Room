/*
 * SignUpActivity.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room.activity.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.login_mvvm_dagger2_room.R
import com.android.login_mvvm_dagger2_room.Utility
import com.android.login_mvvm_dagger2_room.base.BaseActivity
import com.android.login_mvvm_dagger2_room.database.entities.User
import com.android.login_mvvm_dagger2_room.livedata.Failed
import com.android.login_mvvm_dagger2_room.livedata.SignUpSuccess
import com.android.login_mvvm_dagger2_room.livedata.Status
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var signUpViewModel: SignUpViewModel

    override fun getLayoutResource(): Int {
        return R.layout.activity_sign_up
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)

        signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        signUpViewModel.getStatus().observe(this, Observer {
            handleResponse(it)
        })

        btnSignUp.setOnClickListener {
            validateSignUpFields()
        }
    }

    private fun handleResponse(status: Status?) {
        status?.let {
            when (it) {
                is Failed -> {
                    dismissProgressDialog()
                    Toast.makeText(this, "Failed ${it.error}", Toast.LENGTH_LONG).show()
                }
                is SignUpSuccess -> {
                    dismissProgressDialog()
                    Toast.makeText(this, "Sign up successfully", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    /**
     * Validate fields and insert user to local database using view model
     */
    private fun validateSignUpFields() {
        when {
            edtFullName.text!!.isEmpty() -> {
                edtFullName.error = getString(R.string.error_empty_message)
                edtFullName.requestFocus()
            }
            edtSignUpEmail.text!!.isEmpty() -> {
                edtSignUpEmail.error = getString(R.string.error_empty_message)
                edtSignUpEmail.requestFocus()
            }
            !Utility.isValidEmailAddress(edtSignUpEmail.text!!.toString()) -> {
                edtSignUpEmail.error = getString(R.string.error_invalid_email)
                edtSignUpEmail.requestFocus()
            }
            edtSignUpPassword.text!!.isEmpty() -> {
                edtSignUpPassword.error = getString(R.string.error_empty_message)
                edtSignUpPassword.requestFocus()
            }

            edtConfirmPassword.text!!.isEmpty() -> {
                edtConfirmPassword.error = getString(R.string.error_empty_message)
                edtConfirmPassword.requestFocus()
            }
            !edtSignUpPassword.text.toString().equals(edtConfirmPassword.text.toString()) -> {
                edtConfirmPassword.error = getString(R.string.password_not_match)
                edtConfirmPassword.requestFocus()
            }
            else -> {
                Utility.hideKeyboard(this)
                signUp()
            }
        }
    }

    /**
     * Send request to view model.
     */
    private fun signUp() {
        showProgressDialog("Signing up...")
        val user = User()
        user.userName = edtFullName.text!!.toString()
        user.emailAddress = edtSignUpEmail.text!!.toString()
        user.password = edtSignUpPassword.text!!.toString()

        signUpViewModel.insertUserToDatabase(user)

        Handler().postDelayed({
            dismissProgressDialog()
        }, 3000)
    }

    companion object {

        fun startSignUpActivity(context: Context) {
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }
}
