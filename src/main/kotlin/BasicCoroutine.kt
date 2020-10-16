import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking { // # 1 => runs main as with runBlocking
    launch(Dispatchers.Default + CoroutineName("test") + Job()) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }

    val totalTime = measureTimeMillis{
        launch { // # 2 => creates new coroutine
            delay(3_000)
            println("Coroutine thread name 1 ${Thread.currentThread().name}")
        }
        val job = launch(Dispatchers.IO) { // # 3 => creates new coroutine without waiting first launch with IO threads.
            val time = measureTimeMillis {
                println("Coroutine thread name 2 ${Thread.currentThread().name}")
                delay(2_000) // # 3.1 => first suspend function call
                longRunningNetworkCall() // # 3.2 => is executed after completing delay suspend function.
            }
            println("Coroutine two completion time : $time") // # 3.3 => total time of two suspend functions
        }
        println("Function thread name ${Thread.currentThread().name}")
        job.join() // 3.4 => wait until second coroutine.
    }
    println("Total time $totalTime") // # 3 => max time of two coroutines.
}

suspend fun longRunningNetworkCall() {
    println("Making long-running network call")
    delay(3000)
}