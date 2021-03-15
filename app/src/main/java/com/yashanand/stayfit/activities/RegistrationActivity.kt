package com.yashanand.stayfit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yashanand.stayfit.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun registrationBtn(view: View) {
        signUp()
    }

    private fun signUp() {
        if (RegisterEmail.text.toString().isEmpty()) {
            RegisterEmail.error = "Please Enter Email"
            RegisterEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(RegisterEmail.text.toString()).matches()) {
            RegisterEmail.error = "Please Enter valid Email"
            RegisterEmail.requestFocus()
            return
        }
        if (RegisterPassword.text.toString().isEmpty()) {
            RegisterPassword.error = "Please Enter Email"
            RegisterPassword.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(RegisterEmail.text.toString(), RegisterPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(, "createUserWithEmail:success")
                                startActivity(Intent(this, LoginActivity::class.java::class.java))
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    // Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed. Try again after some time...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}