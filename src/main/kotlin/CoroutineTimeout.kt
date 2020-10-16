import kotlinx.coroutines.*
import java.lang.Exception

/**
 * if withTimeout runs with main thread, throws TimeoutCancellationException
 */
fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        println("Network call starting")
        try {
            withTimeout(3000L) {
                kkk()
                println("It cannot reach to me because of timeout")
            }
        }catch (e: TimeoutCancellationException){
            println("Catched bro $e")
        }
    }
    job.join()
    println("Ending long running network call.")
}

suspend fun kkk() {
    println("Making long-running network call")
    delay(3000)
}