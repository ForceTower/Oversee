package dev.forcetower.oversee.networking

import okhttp3.Request

object OverRequests {
    const val AERI_PAGE = "http://aeri.uefs.br"

    @JvmStatic val aeri = Request.Builder()
        .url(AERI_PAGE)
        .build()
}