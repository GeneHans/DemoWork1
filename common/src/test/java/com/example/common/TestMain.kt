package com.example.common

import kotlinx.coroutines.*
import org.junit.Test

class TestMain {
    @Test
    fun testMain() {
        runBlocking {
            printName()
        }
        GlobalScope.launch {

        }
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch {

        }
        job.cancel()
    }

    private suspend fun printName(){
        coroutineScope {
             launch {
                 println("子协程")
             }
        }
    }
}