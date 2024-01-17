package com.androidants.smartcaballocation.ui.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
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
import com.androidants.smartcaballocation.ui.addDriver.AddDriver
import com.androidants.smartcaballocation.ui.splash.SplashActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener , DriverRecyclerAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private var currentLocation: Location? = null
    private lateinit var locationManager: LocationManager
    private lateinit var recyclerAdapter: DriverRecyclerAdapter
    private var selected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
        initViewModel()
        checkPermission()
    }

    private fun setUI (){
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        actionBarDrawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        binding.navView.setNavigationItemSelectedListener {
                when(it.itemId)
                {
                    R.id.home -> {
                        startActivity(Intent(this , MainActivity::class.java))
                        true
                    }
                    R.id.logout -> {
                        logout()
                        true
                    }

                    R.id.add_driver -> {
                        startActivity(Intent(this , AddDriver :: class.java))
                        true
                    }

                    else -> false
                }
        }
        binding.cardAll.setOnClickListener(this)
        binding.cardFree.setOnClickListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.loadingStatus.observe(this){
            binding.progressBar.visibility = it
        }
        viewModel.geocodeResponse.observe(this) {
            it.features[0].properties?.postcode.let {
                lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
                    viewModel.getDrivers(it ?: "262701")
                }
            }
        }
        viewModel.driverList.observe(this) {
            var sortedList = emptyList<Driver>()
            it?.let {
                sortedList = getSortedList(it)
            }
            recyclerAdapter = DriverRecyclerAdapter(sortedList, this , selected , this)
            binding.recyclerView.adapter = recyclerAdapter
        }
        viewModel.logoutStatus.observe(this){
            if (it) {
                startActivity(Intent(this , SplashActivity::class.java))
                finish()
            }
        }
        viewModel.setDriverStatus.observe(this) {
            if (it) {
                Toast.makeText(this, "Driver Booked Successfully", Toast.LENGTH_SHORT).show()
                checkPermission()
            } else
                Toast.makeText(this, "Error while Booking . Try again", Toast.LENGTH_SHORT).show()
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
            lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
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
            R.id.card_all -> {
                selected = 1
                binding.cardAll.setCardBackgroundColor(ContextCompat.getColor(this, R.color.orange))
                binding.cardFree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_500))
                checkPermission()
            }

            R.id.card_free -> {
                selected = 0
                binding.cardAll.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_500))
                binding.cardFree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.orange))
                checkPermission()
            }
        }
    }

    private fun getSortedList(list: List<Driver>) : List<Driver> {

        val lat = currentLocation?.latitude ?: 0.0
        val lon = currentLocation?.longitude ?: 0.0
        val listCpy = mutableListOf<Driver>()
        list.forEach {
            if ( (selected == 0 && it.status == 0) || selected == 1 ) {
                it.distance = calculateDistance(lat , lon , it.latitute.toDouble() , it.longitude.toDouble())
                listCpy.add(it)
            }
        }

        return listCpy.sortedBy { it.distance }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Radius of the earth in km
        val dLat = deg2rad(lat2-lat1)
        val dLon = deg2rad(lon2-lon1)
        val a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                    Math.sin(dLon/2) * Math.sin(dLon/2)
        ;
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        val d = R * c // Distance in km
        return String.format("%.3f", d).toDouble()
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun logout()
    {
        lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
            viewModel.logout()
        }
    }

    private fun createAlertDialog(driver: Driver)
    {
        AlertDialog.Builder(this)
            .setTitle("Book Cab")
            .setMessage("Are you sure you want to book this Cab?")
            .setPositiveButton(
                "Yes"
            ) { dialog, which ->
               lifecycleScope.launch(Dispatchers.IO + Constants.coroutineExceptionHandler) {
                   viewModel.setDriver(driver)
               }
            }
            .setNegativeButton("No", null)
            .setIcon(R.drawable.ic_baseline_car_rental_24)
            .show()
    }

    override fun bookCab(driver: Driver) {
        driver.status = 1
        createAlertDialog(driver)
    }

}