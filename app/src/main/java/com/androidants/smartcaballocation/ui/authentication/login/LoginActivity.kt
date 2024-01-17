package com.androidants.smartcaballocation.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.androidants.smartcaballocation.ui.main.MainActivity
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.common.Constants
import com.androidants.smartcaballocation.databinding.ActivityLoginBinding
import com.androidants.smartcaballocation.ui.authentication.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() , OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.continueBtn.setOnClickListener(this)
        binding.register.setOnClickListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.loadingStatus.observe(this){
            binding.progressBar.visibility = it
        }
        viewModel.loginStatus.observe(this){
            if (!it)
                Toast.makeText(this , "Incorrect email And Password" , Toast.LENGTH_SHORT).show()
            else {
                startActivity(Intent(this , MainActivity::class.java))
                finish()
            }
        }
    }

    private fun checkDetails () {
        if ( binding.editTextEmail.text == null) {
            binding.editTextEmail.error = "Enter email"
            return
        } else if (binding.editTextPassword.text == null) {
            binding.editTextPassword.error = "Enter password"
            return
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()){
            binding.editTextEmail.error = "Enter correct email"
            return
        } else if (binding.editTextPassword.text.length < 6) {
            binding.editTextPassword.error = "must be > 6 characters"
            return
        }
        lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
            viewModel.login(binding.editTextEmail.text.toString() , binding.editTextPassword.text.toString())
        }
    }

    override fun onClick(view: View?) {
        when( view?.id )
        {
            R.id.register -> {
                startActivity(Intent(this@LoginActivity , RegisterActivity::class.java))
                finish()
            }

            R.id.continueBtn -> {
                checkDetails()
            }
        }
    }
}