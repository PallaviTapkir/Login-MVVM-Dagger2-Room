/*
 * Utility.kt
 * Created by Pallavi Tapkir
 */

package com.android.login_mvvm_dagger2_room

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*
import java.util.regex.Pattern

/**
 * Class allows to access all common methods
 *
 */
class Utility {

    companion object {
        /**
         * Hide soft keybord
         */
        fun hideKeyboard(activity: Activity) {

            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Validate email address with regx
         * @param email
         * @return Boolean
         */
        fun isValidEmailAddress(email: String): Boolean {
            return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[cominrg]{2,4})$"
            ).matcher(email).matches()
        }

        /**
         * check internet connection availability
         * @context Application context
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }

        /**
         * Fetch random number between start and end position
         */
        fun random(from: Int, to: Int): Int {
            val random = Random()
            return random.nextInt(to - from) + from
        }

        /**
         * Math.pow(...) is very expensive, so avoid calling it and create it
         * yourself.
         */
        private val POW_10 = intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000)


        fun formatNumber(number: Float, digitCount: Int, separateThousands: Boolean): String {
            return formatNumber(number, digitCount, separateThousands, ',')
        }

        /**
         * Formats the given number to the given number of decimals, and returns the
         * number as a string, maximum 35 characters.
         *
         * @param number
         * @param digitCount
         * @param separateThousands set this to true to separate thousands values
         * @param separateChar      a caracter to be paced between the "thousands"
         * @return
         */
        fun formatNumber(
            number: Float, digitCount: Int, separateThousands: Boolean,
            separateChar: Char
        ): String {

            var inputNumber = number
            var inputDigitCount = digitCount

            val out = CharArray(35)

            var neg = false
            if (inputNumber == 0f) {
                return "0"
            }

            var zero = false
            if (inputNumber < 1 && inputNumber > -1) {
                zero = true
            }

            if (inputNumber < 0) {
                neg = true
                inputNumber = -inputNumber
            }

            if (inputDigitCount > POW_10.size) {
                inputDigitCount = POW_10.size - 1
            }

            inputNumber *= POW_10[inputDigitCount].toFloat()
            var lval = Math.round(inputNumber).toLong()
            var ind = out.size - 1
            var charCount = 0
            var decimalPointAdded = false

            while (lval != 0L || charCount < inputDigitCount + 1) {
                val digit = (lval % 10).toInt()
                lval /= 10
                out[ind--] = (digit + '0'.toInt()).toChar()
                charCount++

                // add decimal point
                if (charCount == inputDigitCount) {
                    out[ind--] = ','
                    charCount++
                    decimalPointAdded = true

                    // add thousand separators
                } else if (separateThousands && lval != 0L && charCount > inputDigitCount) {

                    if (decimalPointAdded) {

                        if ((charCount - inputDigitCount) % 4 == 0) {
                            out[ind--] = separateChar
                            charCount++
                        }

                    } else {

                        if ((charCount - inputDigitCount) % 4 == 3) {
                            out[ind--] = separateChar
                            charCount++
                        }
                    }
                }
            }

            // if number around zero (between 1 and -1)
            if (zero) {
//                out[ind--] = '0'
                charCount += 1
            }

            // if the number is negative
            if (neg) {
//                out[ind--] = '-'
                charCount += 1
            }

            val start = out.size - charCount

            // use this instead of "new String(...)" because of issue < Android 4.0
            return String(out, start, out.size - start)
        }
    }
}