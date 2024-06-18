package com.example.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.storyapp.R
import com.example.storyapp.data.response.ResultState
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.signup.SignUpActivity
import com.example.storyapp.view.story.StoryActivity

class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.hyperlinkRegister.setOnClickListener {
            val signUpActivity = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(signUpActivity)
        }
        binding.loginButton.setOnClickListener {
            if(binding.edLoginEmail.text.isNotEmpty() && binding.passwordToggle.text?.length!! >= 8 ){
                loginViewModel.login( binding.edLoginEmail.text.toString(),
                    binding.passwordToggle.text.toString())

            }else{
                Toast.makeText(this@LoginActivity, "Please fill the form correctly", Toast.LENGTH_SHORT).show()
            }

        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        builder.setView(R.layout.activity_login)
        val dialog: AlertDialog = builder.create()

        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            when (response) {
                is ResultState.Loading -> dialog.show()
                is ResultState.Error -> {
                    dialog.dismiss()
                    Toast.makeText(
                        this@LoginActivity,
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultState.Success -> {
                    dialog.dismiss()
                    val storyActivity = Intent(this@LoginActivity, StoryActivity::class.java)
                    startActivity(storyActivity)
                    finish()
                }
                else -> dialog.dismiss()
            }
        }
    }
}
