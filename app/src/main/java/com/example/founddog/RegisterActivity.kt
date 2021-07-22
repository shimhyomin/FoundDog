package com.example.founddog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val TAG: String = "RegisterActivity"
    }

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        btn_submit.setOnClickListener(this)
        img_clear.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_submit -> {
                register()
            }
            R.id.img_clear -> {
                finish()
            }
        }
    }

    fun register() {
        val email = edt_email.text.toString().trim { it <= ' ' }
        val pwd1 = edt_pwd1.text.toString().trim { it <= ' ' }
        val pwd2 = edt_pwd2.text.toString().trim { it <= ' ' }

        if(validateRegisterDetails(email, pwd1, pwd2)) {
            auth.createUserWithEmailAndPassword(email, pwd1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun validateRegisterDetails(email : String, pwd1 : String, pwd2 : String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(pwd1) -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(pwd2) -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            pwd1 != pwd2 -> {
                Toast.makeText(this,"no email", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }
}