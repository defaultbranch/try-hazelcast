
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

In `standalone-run-and-terminate.kt`:
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

# Client

In `pom.xml`:
```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-client</artifactId>
    <version>3.12.13</version>
</dependency>
```

In `client-run-and-terminate.kt`:
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

## Start Hazelcast Cluster Containers

### Expected Outcome

This sections describes how to start a cluster with two (or more) members,
using [Hazelcast Docker Images](https://hub.docker.com/r/hazelcast/hazelcast).

The expected outcome is to see console output reporting two or more members:
```
Members {size:2, ver:2} [
        Member [192.168.7.133]:5701 - 7f552e54-2f31-40c6-8e74-4105bf367553
        Member [192.168.7.133]:5702 - 5a7dac42-4800-4cae-a92e-118b1ba4446e this
]
```

If you see only one member, something is wrong and will need debugging.

### Default

This reproduces the guide on <https://docs.hazelcast.com/hazelcast/5.3/getting-started/get-started-docker>.

Provide a first member like this:
```bash
podman run --rm -it --network=host -e HZ_CLUSTERNAME="my-cluster" -p 5701:5701 hazelcast/hazelcast
```

Provide a second member like this:
```bash
podman run --rm -it --network=host -e HZ_CLUSTERNAME="my-cluster" -p 5702:5701 hazelcast/hazelcast
```

### Using HZ_NETWORK_PUBLICADDRESS

This reproduces the README on <https://hub.docker.com/r/hazelcast/hazelcast>,
tweaked for `podman` (via `--network=host`).

First, know your host address (eg. via `ip a`); the containers need to open their ports on the host interface.

Provide a first member like this:
```bash
podman run --rm -it --network=host -e HZ_CLUSTERNAME="my-cluster" \
  -e HZ_NETWORK_PUBLICADDRESS=192.168.7.133:5701 -p 5701:5701 hazelcast/hazelcast
```

Provide a second member like this:
```bash
podman run --rm -it --network=host -e HZ_CLUSTERNAME="my-cluster" \
  -e HZ_NETWORK_PUBLICADDRESS=192.168.7.133:5702 -p 5702:5701 hazelcast/hazelcast
```
