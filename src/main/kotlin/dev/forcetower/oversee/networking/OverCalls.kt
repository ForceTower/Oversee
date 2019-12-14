package dev.forcetower.oversee.networking

import dev.forcetower.oversee.impl.OverseeImpl
import okhttp3.Call
import okhttp3.Request

object OverCalls {
    @JvmStatic
    private fun createCall(request: Request): Call {
        val client = OverseeImpl.instance.client
        return client.newCall(request)
    }

    @JvmStatic
    val aeri: Call
        get() {
            val request = OverRequests.aeri
            return createCall(request)
        }
}