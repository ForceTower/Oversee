package dev.forcetower.oversee.module

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume

interface Module<T> {
    suspend fun execute(): T

    suspend fun Call.executeSuspend() = suspendCancellableCoroutine<Response> { continuation ->
        this.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }
        })

        continuation.invokeOnCancellation { this.cancel() }
    }
}