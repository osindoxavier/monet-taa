package com.xavier.moneytaa.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.xavier.moneytaa.data.local.dao.UserDao
import com.xavier.moneytaa.domain.model.authentication.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createAccountWithEmail(email: String, password: String): Flow<AuthResponse>
    fun loginWithEmail(email: String, password: String): Flow<AuthResponse>
    fun sigInWithGoogle(): Flow<AuthResponse>
    fun getCurrentUser(): FirebaseUser?

    suspend fun signOut()

    suspend fun saveFirebaseUserLocally(firebaseUser: FirebaseUser)

}