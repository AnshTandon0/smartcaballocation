package com.androidants.smartcaballocation.data.repository

import com.androidants.smartcaballocation.data.ApiCalls
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api : ApiCalls ,
    private val firebaseAuth: FirebaseAuth ,
    private val firestore: FirebaseFirestore
) : MainRepository {

    override suspend fun checkUserAuthStatus(): Boolean {
        return firebaseAuth.currentUser !=  null
    }

    override suspend fun login(email: String , password : String): Boolean {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun register(email: String , password : String): Boolean {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun logout(): Boolean {
        firebaseAuth.signOut()
        if ( FirebaseAuth.getInstance().currentUser == null )
            return true
        return false
    }

    override suspend fun setUserDetail(user: User): Boolean {
        val result = firestore.collection("User").document(user.email).set(user)
        return result.isSuccessful
    }

    override suspend fun getUserDetail(email: String) : User?  {
        val result = firestore.collection("User").document(email).get()
        return result.result?.toObject(User::class.java)
    }

    override suspend fun setDriver(driver: Driver): Boolean {
        val result = firestore.collection("Location")
            .document("Driver").collection(driver.pincode).document(driver.email).set(driver)
        return result.isSuccessful
    }

    override suspend fun getDrivers(pincode: String): List<Driver>? {
        val result = firestore.collection("Location")
            .document("Driver").collection(pincode).get().await()
        return result.toObjects(Driver::class.java)
    }

    override suspend fun getAllDrivers(): List<Driver>? {
        TODO("Not yet implemented")
    }

    override suspend fun reverseGeocode(lat: Double, lon: Double, apiKey: String): GeoCodeResponse {
        return api.getCoins(lat , lon , apiKey)
    }

}
