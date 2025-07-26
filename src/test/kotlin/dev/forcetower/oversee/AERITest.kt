package dev.forcetower.oversee

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AERITest : BaseTest() {
    @Test
    fun extractionTest() = runBlocking {
        val items = instance.getAERINews()
        assertEquals(6, items.size)
    }
}