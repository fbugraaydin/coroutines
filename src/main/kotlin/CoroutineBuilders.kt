import Common.Companion.heavyFetch1
import Common.Companion.heavyFetch2
import kotlinx.coroutines.*

fun main()= runBlocking<Unit> {

        launch {
            heavyFetch1("Launch ")
            heavyFetch2("Launch ")
        }
        println("------ Completed Launch Stage --------")

        runBlocking {
            heavyFetch1("runBlocking ")
            heavyFetch2("runBlocking ")
        }

        println("------ Completed RB Stage -------")

        runBlocking {
            val async = async { heavyFetch1("Async ") }
            val async1 = async { heavyFetch2("Async ") }
            println("Completed Async: ${async.await()} ${async1.await()}")
        }

        println("------ Completed Async Stage -------")

}
