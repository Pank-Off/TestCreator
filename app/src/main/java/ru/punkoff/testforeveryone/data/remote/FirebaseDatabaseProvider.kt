package ru.punkoff.testforeveryone.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ru.punkoff.testforeveryone.data.errors.NoAuthException
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val TESTS_COLLECTION = "tests"
private const val USERS_COLLECTION = "users"

class FirebaseDatabaseProvider(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FirebaseProvider {
    private val currentUser
        get() = auth.currentUser

    override suspend fun pushTest(test: TestEntity) {
        suspendCoroutine<TestEntity> { continuation ->
            try {
                getUserTestCollection().document(test.testId.toString()).set(test)
                    .addOnSuccessListener {
                        Log.d(javaClass.simpleName, "Document has been saved")
                        continuation.resumeWith(Result.success(test))
                    }
                    .addOnFailureListener {
                        Log.e(javaClass.simpleName, "Document has not been saved")
                        continuation.resumeWithException(it)
                    }
            } catch (exc: NoAuthException) {
                Log.e(javaClass.simpleName, "Error saving test $test, message: ${exc.message}")
                return@suspendCoroutine
            }
        }
    }

    private fun getUserTestCollection() = currentUser?.let {
        firestore.collection(USERS_COLLECTION).document(it.uid).collection(TESTS_COLLECTION)
    } ?: throw NoAuthException()
}