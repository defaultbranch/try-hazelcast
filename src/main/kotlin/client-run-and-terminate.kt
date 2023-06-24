import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig

fun main() {

    val config = ClientConfig().apply { clusterName = "my-cluster" }
    val client = HazelcastClient.newHazelcastClient(config)

    val customers = client.getMap<Int, String>("customers")
    customers.apply {
        put(1, "Joe")
        put(2, "Ali")
    }

    println("Customer with key1: ${customers[1]}")
    println("Customer count: ${customers.size}")
}
