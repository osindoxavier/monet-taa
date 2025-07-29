package com.xavier.moneytaa.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.xavier.moneytaa.R
import com.xavier.moneytaa.data.local.dao.UserDao
import com.xavier.moneytaa.data.local.entity.UserEntity
import com.xavier.moneytaa.domain.model.authentication.AuthResponse
import com.xavier.moneytaa.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
    private val userDao: UserDao
) : AuthRepository {
    override fun createAccountWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createAccountWithEmail: ${task.result.user}")
                    task.result.user?.let { user ->
                        trySend(AuthResponse.Success(user))
                    }
                } else {
                    Log.e(TAG, "createAccountWithEmail: ${task.exception?.message}")
                    trySend(AuthResponse.Error(task.exception?.message ?: "Unknown Error"))
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "createAccountWithEmail: ${it.message}", it)
                trySend(AuthResponse.Error(it.message.toString()))
            }
        awaitClose()
    }

    override fun loginWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "loginWithEmail: ${task.result.user}")
                    task.result.user?.let { user ->
                        trySend(AuthResponse.Success(user))
                    }
                } else {
                    Log.e(TAG, "loginWithEmail: ${task.exception?.message}")
                    trySend(AuthResponse.Error(task.exception?.message ?: "Unknown Error"))
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "loginWithEmail: ${it.message}", it)
                trySend(AuthResponse.Error(it.message.toString()))
            }
        awaitClose()
    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val nd = MessageDigest.getInstance("SHA-256")

        val digest = nd.digest(bytes)

        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }

    override fun sigInWithGoogle(): Flow<AuthResponse> = callbackFlow {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.wec_client_id))
            .setAutoSelectEnabled(false)
            // nonce string to use when generating a Google ID token
            .setNonce(createNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context = context,
                request = request,
            )

            val credential = result.credential
            if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdTokenCredential.idToken,
                                null
                            )

                        firebaseAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.d(TAG, "sigInWithGoogle: ${it.result.user}")
                                    it.result.user?.let { user ->
                                        trySend(AuthResponse.Success(user))
                                    }
                                } else {
                                    Log.e(
                                        TAG,
                                        "sigInWithGoogle: ${it.exception?.message}",
                                        it.exception
                                    )
                                    trySend(AuthResponse.Error(it.exception?.message ?: ""))
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "sigInWithGoogle: ${e.message}", e)
                    }
                    awaitClose()
                }
            }


        } catch (e: Exception) {
            Log.e(TAG, "sigInWithGoogle: ${e.message}", e)
            trySend(AuthResponse.Error(e.message ?: ""))

        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun signOut() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            Log.e(TAG, "signOut: ${e.message}", e)
        }
    }

    override suspend fun saveFirebaseUserLocally(
        firebaseUser: FirebaseUser
    ) {
        val userEntity = UserEntity(
            uid = firebaseUser.uid,
            email = firebaseUser.email ?: "",
            displayName = firebaseUser.displayName
        )
        userDao.insertUser(userEntity)
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}