package com.example.loginauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.loginauthentication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Incorrect Email or Password ", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }

        }
    }
    private fun emailFocusListener()
    {
        binding.emailEt.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.emailLayout.helperText = validEmail()
            }

        }
    }
    private fun validEmail():String?
    {
        val emailText = binding.emailEt.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.passET.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.passwordLayout.helperText = validPassword()
            }
        }
    }
    private fun validPassword():String?
    {
        val passwordText = binding.passET.text.toString()

        if(passwordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-Case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-Case Character"
        }
        if(!passwordText.matches(".*[!@#$%^&*+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character(!@#$%^&*+=)"
        }
        if(!passwordText.matches(".*[0-9].*".toRegex()))
        {
            return "Must Contain 1 Number"
        }
        return null
    }

}
