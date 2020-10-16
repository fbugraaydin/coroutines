import Common.Companion.longRunningNetworkCall
import Common.Companion.longRunningNetworkCall2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    GlobalScope.launch {
        val time = measureTimeMillis {
            val result1 = async { longRunningNetworkCall() }
            val result2 = async { longRunningNetworkCall2() }
            println("Network Call Response 1 => ${result1.await()}")
            println("Network Call Response 2 => ${result2.await()}")
        }

        println("Took $time ms.")
    }
}