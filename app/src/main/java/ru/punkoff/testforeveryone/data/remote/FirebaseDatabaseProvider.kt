package ru.punkoff.testforeveryone.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
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

    @ExperimentalCoroutinesApi
    private val result = MutableStateFlow<List<TestEntity>?>(null)
    private var subscribeOnDb = false
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

    @ExperimentalCoroutinesApi
    override fun observeTests(): Flow<List<TestEntity>> {
        if (!subscribeOnDb) subscribeForDbChanging()
        return result.filterNotNull()
    }

    @ExperimentalCoroutinesApi
    private fun subscribeForDbChanging() {
        if (subscribeOnDb) return
        try {
            getUserTestCollection().get()
                .addOnSuccessListener { snapshot ->
                    val tests = mutableListOf<TestEntity>()
                    for (document in snapshot) {
                        Log.d(javaClass.simpleName, "${document.id} => ${document.data}")
                        Log.d(
                            javaClass.simpleName,
                            "documentObject: ${document.toObject(TestEntity::class.java)}"
                        )
                        val currentTest = document.toObject(TestEntity::class.java)
                        tests.add(currentTest)
                    }
                    result.value = tests
                }.addOnFailureListener {
                    Log.e(javaClass.simpleName, it.stackTraceToString())
                }
        } catch (exc: NoAuthException) {
            Log.e(javaClass.simpleName, exc.stackTraceToString())
            result.value = emptyList()
        }
    }

    private fun getUserTestCollection() = currentUser?.let {
        firestore.collection(TESTS_COLLECTION)//.document(it.uid).collection(TESTS_COLLECTION)
    } ?: throw NoAuthException()
}