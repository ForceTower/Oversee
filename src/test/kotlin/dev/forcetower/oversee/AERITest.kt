package dev.forcetower.oversee

import org.junit.Assert.assertEquals
import org.junit.Test

class AERITest : BaseTest() {
    @Test
    suspend fun extractionTest() {
        val items = instance.getAERINews()
        assertEquals(6, items.size)
    }
}