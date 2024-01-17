package com.androidants.smartcaballocation.ui.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.common.Constants
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private var currentLocation: Location? = null
    private lateinit var locationManager: LocationManager
    private lateinit var recyclerAdapter: DriverRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        checkPermission()
        binding.back.setOnClickListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.geocodeResponse.observe(this) {
            Log.d("yyyyyyyy" , currentLocation?.latitude.toString())
            it.features[0].properties?.postcode.let {
                lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
                    viewModel.getDrivers(it ?: "262701")
                }
            }
        }
        viewModel.driverList.observe(this) {
            recyclerAdapter = DriverRecyclerAdapter(
                it ?: emptyList(),
                this,
                currentLocation?.latitude?.toFloat() ?: 0f,
                currentLocation?.longitude?.toFloat() ?: 0f
            )
            binding.recyclerView.adapter = recyclerAdapter
        }
        viewModel.setDriverStatus.observe(this){
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d("yyyyyyyy" , currentLocation?.latitude.toString())
                viewModel.reverseGeocode(
                    currentLocation?.latitude ?: 0.0,
                    currentLocation?.longitude ?: 0.0,
                    Constants.API_KEY
                )
            }
        }
    }

    private fun checkPermission() {
        val result = ContextCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION)
        if (result != PackageManager.PERMISSION_GRANTED)
            requestPermission()
        else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            currentLocation =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lifecycleScope.launch(Dispatchers.IO ) {
                viewModel.reverseGeocode(
                    currentLocation?.latitude ?: 0.0,
                    currentLocation?.longitude ?: 0.0,
                    Constants.API_KEY
                )
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), Constants.PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (!locationAccepted) {
                    Snackbar.make(
                        binding.root,
                        "Permission Denied, You cannot access the Application.",
                        Snackbar.LENGTH_LONG
                    ).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(
                                "You need to allow access the permissions",
                                { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermission()
                                    }
                                },
                                { dialog, which ->
                                    this@MainActivity.finish()
                                })
                            return
                        }
                    }
                }
            }
        }
    }


    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener,
        cancelListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", cancelListener)
            .create()
            .show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.back -> {
//                finish()
                    lifecycleScope.launch (Dispatchers.IO + Constants.coroutineExceptionHandler) {
                        viewModel.setDriver(
                            Driver("Driver 1" ,
                                "abc@gmail.com" ,
                                "1234567890",
                                23.8168f ,
                                86.4432f ,
                                "India" ,
                                "JharKhand" ,
                                "Dhanbad" ,
                                "826005"
                            )
                        )
                    }
            }
        }
    }
}