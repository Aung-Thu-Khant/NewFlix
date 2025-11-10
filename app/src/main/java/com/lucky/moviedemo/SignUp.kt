package com.lucky.moviedemo

import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.lucky.moviedemo.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.btnSignUp.setOnClickListener {
            var name = binding.tiName.editText?.text.toString()
            var email = binding.timail.editText?.text.toString()
            var password = binding.tiPassword.editText?.text.toString()
            var confirmPassword = binding.tiConfirmPassword.editText?.text.toString()
            if(TextUtils.isEmpty(name)){
                binding.tiName.error = "Name is required"
                binding.tiName.requestFocus()
            }else if(TextUtils.isEmpty(email)){
                binding.timail.error = "Email is required"
                binding.timail.requestFocus()
            }else if(TextUtils.isEmpty(password)){
                binding.tiPassword.error = "Password is required"
                binding.tiPassword.requestFocus()
            }else if(TextUtils.isEmpty(confirmPassword)){
                binding.tiConfirmPassword.error = "Confirm Password is required"
                binding.tiConfirmPassword.requestFocus()
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(this,"Password and Confirm Password must be same",Toast.LENGTH_SHORT).show()
            }else{
                registerAccount(name,email,password)
            }
        }

        setContentView(binding.root)

    }

    private fun registerAccount(name: String, email: String, password: String) {
        if(checkInternetConnection()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Registration Failed!",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInternetConnection() : Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}