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
import com.lucky.moviedemo.databinding.ActivityForgetPasswordBinding

class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            var email = binding.etEmail.editText?.text.toString()
            if(TextUtils.isEmpty(email)){
                binding.etEmail.error = "Email is required"
                binding.etEmail.requestFocus()
            }else{
                if(checkInternetConnection()){
                    sendPasswordResetLink(email)
                }else{
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)

    }

    private fun sendPasswordResetLink(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Password Reset Link Sent to your Email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
                finish()
            }else{
                Toast.makeText(this,"Failed to send Password Reset Link",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInternetConnection() : Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}