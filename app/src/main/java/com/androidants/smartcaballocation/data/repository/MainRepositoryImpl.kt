package com.androidants.smartcaballocation.data.repository

import com.androidants.smartcaballocation.data.ApiCalls
import com.androidants.smartcaballocation.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api : ApiCalls ,
    private val firebaseAuth: FirebaseAuth
) : MainRepository {

    override suspend fun checkUserAuthStatus(): Boolean {
        return firebaseAuth.currentUser !=  null
    }

    override suspend fun login(email: String , password : String): Boolean {
        return try {
            val result = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun register(email: String , password : String): Boolean {
        return try {
            val result = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun logout(): Boolean {
        FirebaseAuth.getInstance().signOut()
        if ( FirebaseAuth.getInstance().currentUser == null )
            return true
        return false
    }

    override suspend fun addUserDetail(user: User): Boolean {
        val result = FirebaseFirestore.getInstance().collection("User").add(user).await()
        return true
    }

}