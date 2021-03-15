package com.yashanand.stayfit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yashanand.stayfit.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        NewRegistration.setOnClickListener {
            Intent(this, RegistrationActivity::class.java).also { startActivity(it) }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser : FirebaseUser?){
        if (currentUser != null){
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
                //finish()
            }else{
                Toast.makeText(this, "Please verify email address.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginBtn(view: View) {
        if (LoginEmail.text.toString().isEmpty()){
            LoginEmail.error="Please Enter Email"
            LoginEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(LoginEmail.text.toString()).matches()){
            LoginEmail.error="Please Enter valid Email"
            LoginEmail.requestFocus()
            return
        }
        if (LoginPassword.text.toString().isEmpty()){
            LoginPassword.error="Please Enter Password."
            LoginPassword.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(LoginEmail.text.toString(), LoginPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // ...
                }

            }

    }
}