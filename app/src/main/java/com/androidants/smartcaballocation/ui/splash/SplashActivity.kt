package com.androidants.smartcaballocation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.ui.authentication.register.RegisterActivity
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel : SplashViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SplashViewModal::class.java)
        viewModel.authStatus.observe(this){
            initAnimation(it)
        }
    }

    private fun initAnimation(auth : Boolean) {
       startActivity(Intent(this , RegisterActivity::class.java))
       finish()
    }
}