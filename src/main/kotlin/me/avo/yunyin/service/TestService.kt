package me.avo.yunyin.service

import org.springframework.stereotype.Service

@Service
class TestService {

    fun testMultiply(x: Int, y: Int): Int {
        Thread.sleep(250)
        println("TEST!")
        return x * y
    }

    fun throwException() {
        Thread.sleep(100)
        throw IllegalStateException("Expected")
    }
}