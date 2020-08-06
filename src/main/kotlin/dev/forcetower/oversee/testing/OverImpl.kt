package dev.forcetower.oversee.testing

class OverImpl : OverContract {
    override fun method1(a: Int, b: Int): Int {
        val res = a * b + 4
        println("$res 2")
        return res
    }
}