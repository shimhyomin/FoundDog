package com.example.founddog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val TAG: String = "LoginActivity"
    }

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)

    }

//    override fun onStart() {
//        super.onStart()
//        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//        finish()
//    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> {
                loginUser()

            }
            R.id.btn_register -> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun loginUser() {
        val email = edt_email.text.toString().trim { it <= ' ' }
        val pwd = edt_pwd.text.toString().trim { it <= ' ' }

        if(validateLoginDetails(email, pwd)) {
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }

    private fun validateLoginDetails(email : String, pwd : String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(pwd) -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }
}