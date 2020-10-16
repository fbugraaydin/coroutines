import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class Common {
    companion object {
        suspend fun longRunningCalculation(): String {
            delay(3000)
            return "1000"
        }

        suspend fun longRunningNetworkCall(): String {
            println("network call 1")
            delay(4000)
            return "Call 1"
        }

        suspend fun longRunningNetworkCall2(): String {
            println("network call 2")
            delay(4000)
            return "Call 2"
        }

        suspend fun heavyFetch1(type: String) {
            withContext(Dispatchers.Default) {
                println(type + "Heavy Fetch1 started")
                delay(2000)
                println(type + "Heavy Fetch1 completed")
                println(type + "Heavy Fetch1 Thread name => " + Thread.currentThread().name)
            }
        }

        suspend fun heavyFetch2(type: String) {
            withContext(Dispatchers.Default) {
                println(type + "Heavy Fetch2 started")
                delay(1000)
                println(type + "Heavy Fetch2 completed")
                println(type + "Heavy Fetch2 Thread name => " + Thread.currentThread().name)
            }
        }
    }
}