package dev.forcetower.oversee

import dev.forcetower.oversee.impl.OverseeImpl
import dev.forcetower.oversee.model.NewsMessage

abstract class Oversee {
    abstract suspend fun getAERINews(): List<NewsMessage>

    companion object {
        @JvmStatic
        val instance: Oversee
            get() = OverseeImpl.instance

        @JvmStatic
        fun initialize() = OverseeImpl.initialize()
    }
}