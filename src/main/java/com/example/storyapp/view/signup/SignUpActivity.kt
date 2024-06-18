package com.example.storyapp.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.storyapp.data.response.ResultState
import com.example.storyapp.databinding.ActivitySignUpBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.login.LoginActivity


class SignUpActivity : AppCompatActivity() {
    private val signupViewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val loginActivity = Intent(this, LoginActivity::class.java)
        binding.apply {
            hyperlinkLogin.setOnClickListener {
                startActivity(loginActivity)
            }

            registerButton.setOnClickListener {
                if (edRegisterEmail.text.isNotEmpty() && edRegisterName.text.isNotEmpty() && edRegisterPassword.text?.length!! >= 8) {
                    signupViewModel.submitRegister(
                        name = edRegisterName.text.toString(),
                        email = edRegisterEmail.text.toString(),
                        password = edRegisterPassword.text.toString()
                    )
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Please fill in the form ",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@SignUpActivity)
//            builder.setView(R.layout.activity_sign_up)
            val dialog: AlertDialog = builder.create()

            signupViewModel.responseResult.observe(this@SignUpActivity) { response ->
                when (response) {
                    is ResultState.Loading -> dialog.show()
                    is ResultState.Error -> {
                        dialog.dismiss()
                        Toast.makeText(
                            this@SignUpActivity,
                            response.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ResultState.Success -> {
                        dialog.dismiss()
                        val detailActivity = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(detailActivity)
                        finish()
                    }

                    else -> dialog.dismiss()
                }
            }
        }
    }
}