package com.lucky.moviedemo

import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.lucky.moviedemo.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val sharedPref = getSharedPreferences("SaveInfo", MODE_PRIVATE)
        val isChecked = sharedPref.getBoolean("isChecked", false)
        if(isChecked){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        binding.btnLogin.setOnClickListener {
            var email = binding.timail.editText?.text.toString()
            var password = binding.tiPassword.editText?.text.toString()
            if(TextUtils.isEmpty(email)){
                binding.timail.error = "Email is required"
                binding.timail.requestFocus()
            }else if(TextUtils.isEmpty(password)){
                binding.tiPassword.error = "Password is required"
                binding.tiPassword.requestFocus()
            }else{
                if(checkInternetConnection()){
                    loginAccount(email,password)
                }else{
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.txtForgotPassword.setOnClickListener {
            startActivity(android.content.Intent(this, ForgetPassword::class.java))
        }

        setContentView(binding.root)

    }
    private fun checkInternetConnection() : Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    private fun loginAccount(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                if(binding.checkBox.isChecked){
                    val sharedPref = getSharedPreferences("SaveInfo", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putBoolean("isChecked", true)
                    editor.apply()
                }else{
                    val sharedPref = getSharedPreferences("SaveInfo", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("isChecked", false)
                    editor.apply()
                }
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                startActivity(android.content.Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"Login Failed!",Toast.LENGTH_SHORT).show()
            }
        }
    }

}