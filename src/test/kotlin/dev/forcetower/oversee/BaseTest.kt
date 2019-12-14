package dev.forcetower.oversee

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.BeforeClass

abstract class BaseTest {
    companion object {
        lateinit var instance: Oversee
        lateinit var gson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            gson = GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create()
            Oversee.initialize()
            instance = Oversee.instance
        }

        protected fun toJson(source: Any) = gson.toJson(source)
    }
}