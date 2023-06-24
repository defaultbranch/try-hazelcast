import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast

fun main() {

    val cfg = Config()
    val instance = Hazelcast.newHazelcastInstance(cfg)

    val customers = instance.getMap<Int, String>("customers")
    customers.apply {
        put(1, "Joe")
        put(2, "Ali")
    }

    println("Customer with key1: ${customers[1]}")
    println("Customer count: ${customers.size}")

    instance.shutdown()
}
