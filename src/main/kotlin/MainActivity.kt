package com.ashgem.coroutines

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.textView = findViewById(R.id.textView)

        //basicCoroutine()
        //coroutineScopeRunBlocking()
        //basicInteractionWithUI()
        //coroutineJobs()
        //coroutineTimeout()
        //dispatcherTypes()
        //retrofit(textView)
        //coroutineAsync()
        //cancellationCoroutine()

        runBlocking {
            launch(Dispatchers.Default + CoroutineName("test")) {
                println("I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }


    private fun cancellationCoroutine() {
        val TAG = "CancellationCoroutine"
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                try{
                    var nextPrintTime = startTime
                    var i = 0
                    while (i<10) { // computation loop, just wastes CPU
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            Log.d(TAG, "job: I'm sleeping ${i++} ...")
                            nextPrintTime += 500L
                        }
                    }
                }finally {
                    Log.d(TAG,"Canceled Jobb")
                }
            }
            delay(1000L) // delay a bit
            Log.d(TAG,"main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            Log.d(TAG,"main: Now I can quit.")
        }
    }

    private fun basicCoroutine() {

        val TAG = "Coroutine"
        GlobalScope.launch {
            delay(3_000)
            Log.d(TAG, "Coroutine thread name 1 ${Thread.currentThread().name}")
        }

        GlobalScope.launch {
            delay(2_000)
            Log.d(TAG, "Coroutine thread name 2 ${Thread.currentThread().name}")
        }

        Log.d(TAG, "Function thread name ${Thread.currentThread().name}")
    }

    private fun coroutineScopeRunBlocking() {
        val TAG = "CoroutineScope"

        runBlocking { // this: CoroutineScope
            launch {
                delay(200L)
                Log.d(TAG,"Task from runBlocking")
            }

            coroutineScope { // Creates a coroutine scope. It is a suspend function, because of that waits complete.
                launch {
                    delay(500L)
                    Log.d(TAG,"Task from nested launch")
                }

                delay(100L)
                Log.d(TAG,"Task from coroutine scope") // This line will be printed before the nested launch
            }

            Log.d(TAG,"Coroutine scope is over") // This line is not printed until the nested launch completes
        }
    }

    private fun basicInteractionWithUI(){
        val TAG = "BasicInteraction"

        GlobalScope.launch(Dispatchers.Default){
            Log.d(TAG,"Coroutine thread name ${Thread.currentThread().name}")
            val result = longRunningCalculation()
            withContext(Dispatchers.Main){
                Log.d(TAG,"Set to TextView thread name ${Thread.currentThread().name}")
                textView.text = result
            }
        }
    }

    private fun coroutineJobs() {
        val TAG = "CoroutineJobs"
        val job = GlobalScope.launch(Dispatchers.IO) {
            repeat(5){
                Log.d(TAG,"Coroutine is continuing")
                delay(1000)
            }
        }

        runBlocking {
            job.join() // waits to finish
            //delay(2000)
            //job.cancel() // cancels after two times.
            Log.d(TAG,"Main thread is continuing")
        }

    }

    /**
     * if withTimeout runs with main thread, throws TimeoutCancellationException
     */
    private fun coroutineTimeout() {
        val TAG = "CoroutineTimeout"
        val job = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG,"Network call starting")
            withTimeout(3000L){
                longRunningNetworkCall()
                Log.d(TAG,"Network call ending")
            }
        }

        runBlocking {
            job.join()
            Log.d(TAG,"Ending long running network call.")
        }
    }

    private fun coroutineAsync() {
        val TAG = "CoroutineAsync"
        GlobalScope.launch {
            var time = measureTimeMillis {
                val result1 = async { longRunningNetworkCall() }
                val result2 = async { longRunningNetworkCall2() }
                Log.d(TAG,"Network Call Response 1 => ${result1.await()}")
                Log.d(TAG,"Network Call Response 2 => ${result2.await()}")
            }

            Log.d(TAG,"Took $time ms.")
        }
    }


    private suspend fun longRunningCalculation():String{
        delay(3000)
        return "1000"
    }

    private suspend fun longRunningNetworkCall():String{
        Log.d("Coroutine","Nerwork call 1")
        delay(4000)
        return "Call 1"
    }

    private suspend fun longRunningNetworkCall2():String{
        Log.d("Coroutine","Nerwork call 2")
        delay(4000)
        return "Call 2"
    }

}


