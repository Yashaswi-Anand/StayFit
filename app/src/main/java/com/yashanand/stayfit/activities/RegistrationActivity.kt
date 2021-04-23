package com.yashanand.stayfit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yashanand.stayfit.R
import com.yashanand.stayfit.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var auth: FirebaseAuth
   internal lateinit var googleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        Glide.with(this).asGif().load(R.raw.logo).into(regLogo)

            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if (user != null) {  // if user is not authenticate ,send him to sign in
                startActivity(Intent(this, DashboardActivity::class.java))
            }

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)

        back_signup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
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


    // sign in with google gmail directly -------------------------

    fun registerWithGoogleDirectly(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SignInActivity", "signInWithCredential:failure")
                }
            }
    }


}