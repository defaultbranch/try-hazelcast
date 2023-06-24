
# Standalone run-and-terminate member

What it does:

- starts a Hazelcast cluster (embedded)
- will quickly add and retrieve data through he Hazelcast API
- will shutdown after the blocking IO

In `pom.xml`:
```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast</artifactId>
    <version>5.3.1</version>
</dependency>
```

In `main.kt`:
```kotlin
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
```

Runs as expected.
