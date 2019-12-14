package dev.forcetower.oversee.impl

import dev.forcetower.oversee.Oversee
import dev.forcetower.oversee.module.aeri.AERIModule
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OverseeImpl : Oversee() {
    val client = createClient()

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    override fun getAERINews() = AERIModule().finishedResult

    companion object {
        private lateinit var sDefaultInstance: OverseeImpl
        private val sLock = Any()

        val instance: OverseeImpl
            get() = synchronized(sLock) {
                if (::sDefaultInstance.isInitialized)
                    return sDefaultInstance
                else
                    throw IllegalStateException("Oversee was not initialized")
            }

        fun initialize() {
            synchronized(sLock) {
                if (!::sDefaultInstance.isInitialized) {
                    sDefaultInstance = OverseeImpl()
                }
            }
        }
    }
}