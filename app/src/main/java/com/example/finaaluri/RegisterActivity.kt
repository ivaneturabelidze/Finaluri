package com.example.finaaluri

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var regEmailEditText: EditText
    private lateinit var regPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var logInButton: Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        regEmailEditText = findViewById(R.id.regEmailEditText)
        regPasswordEditText = findViewById(R.id.regPwdEditText)
        registerButton = findViewById(R.id.registerButton)
        logInButton = findViewById(R.id.loginPageButton)

        logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        registerButton.setOnClickListener {
            val email: String = regEmailEditText.text.toString()
            val password: String = regPasswordEditText.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_SHORT).show()
            }
            else if(regPasswordEditText.length() < 6){
                Toast.makeText(this, "პაროლი უნდა შეიცავდეს მინიმუმ 6 სიმბოლოს", Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(email).build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener(OnCompleteListener<Void?>{userUpdated->
                                if(userUpdated.isSuccessful) {
                                    navigateToMain()
                                }
                            })
                            } else {
                                // If sign in fails, display a message to the user.
                                    Toast.makeText(baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }

                        }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            navigateToMain()
        }
    }
    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}