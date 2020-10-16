import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val job = GlobalScope.launch(Dispatchers.IO) {
        repeat(5){
            println("Coroutine is continuing")
            delay(1000)
        }
    }

    runBlocking {
        job.join() // waits to finish
        //delay(2000)
        //job.cancel() // cancels after two times.
        println("Main thread is continuing")
    }
}