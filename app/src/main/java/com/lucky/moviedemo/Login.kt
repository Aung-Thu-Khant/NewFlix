package com.lucky.moviedemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lucky.moviedemo.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)


        binding.btnSignUp.setOnClickListener {
            startActivity(android.content.Intent(this, SignUp::class.java))
        }

        setContentView(binding.root)

    }
}