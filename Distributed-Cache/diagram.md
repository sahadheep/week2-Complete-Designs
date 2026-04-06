classDiagram
    class IDistributionStrategy {
        <<interface>>
        +getTargetNodeIndex(String key, int numberOfNodes) int
    }

    class ModuloDistributionStrategy {
        +getTargetNodeIndex(String key, int numberOfNodes) int
    }

    class IEvictionPolicy~K~ {
        <<interface>>
        +keyAccessed(K key) void
        +evictKey() K
    }

    class LRUEvictionPolicy~K~ {
        -LinkedHashSet~K~ keyOrder
        +keyAccessed(K key) void
        +evictKey() K
    }

    class CacheNode~K, V~ {
        -int capacity
        -Map~K, V~ storage
        -IEvictionPolicy~K~ evictionPolicy
        +get(K key) V
        +put(K key, V value) void
    }

    class Database~K, V~ {
        -Map~K, V~ dbStorage
        +fetch(K key) V
        +save(K key, V value) void
    }

    class DistributedCache~K, V~ {
        -List~CacheNode~ nodes
        -IDistributionStrategy distributionStrategy
        -Database~K, V~ database
        +get(K key) V
        +put(K key, V value) void
    }

    IDistributionStrategy <|.. ModuloDistributionStrategy : Realization
    IEvictionPolicy <|.. LRUEvictionPolicy : Realization
    CacheNode o-- IEvictionPolicy : Aggregation
    DistributedCache o-- CacheNode : Composition
    DistributedCache --> IDistributionStrategy : Uses
    DistributedCache --> Database : Uses
