package com.ashgem.coroutines

import android.util.Log
import kotlinx.coroutines.*

class DispatcherTypes {

    companion object{

        private val TAG = "DispatcherTypes"

        fun dispatcherTypes() {

            GlobalScope.launch {
                heavyFetch1("Launch ")
                heavyFetch2("Launch ")
            }
            Log.d(TAG,"------ Completed Launch Stage --------")

            runBlocking {
                heavyFetch1("RB ")
                heavyFetch2("RB ")
            }

            Log.d(TAG,"------ Completed RB Stage -------")

            runBlocking {
                val async = async { heavyFetch1("Async ") }
                val async1 = async { heavyFetch2("Async ") }
                println("Completed Async: ${async.await()} ${async1.await()}")
            }

            Log.d(TAG,"------ Completed Async Stage -------")

        }

        private suspend fun heavyFetch1(type: String) {
            withContext(Dispatchers.Default) {
                Log.d(TAG,type + "Heavy Fetch1 started")
                Thread.sleep(2000)
                Log.d(TAG,type + "Heavy Fetch1 completed")
                Log.d(TAG,type + "Heavy Fetch1 Thread name => " + Thread.currentThread().name)
            }
        }

        private suspend fun heavyFetch2(type: String) {

            withContext(Dispatchers.Default) {
                Log.d(TAG,type + "Heavy Fetch2 started")
                Thread.sleep(1000)
                Log.d(TAG,type + "Heavy Fetch2 completed")
                Log.d(TAG,type + "Heavy Fetch2 Thread name => " + Thread.currentThread().name)
            }
        }
    }



}