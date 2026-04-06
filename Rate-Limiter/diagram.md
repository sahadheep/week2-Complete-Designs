classDiagram
    class IRateLimitStrategy {
        <<interface>>
        +isAllowed() boolean
    }

    class FixedWindowStrategy {
        -int maxRequests
        -long windowSizeInMs
        -AtomicInteger counter
        -long windowStartTime
        +isAllowed() boolean
    }

    class SlidingWindowStrategy {
        -int maxRequests
        -long windowSizeInMs
        -ConcurrentLinkedQueue timestamps
        +isAllowed() boolean
    }

    class RateLimitManager {
        -Map userStrategies
        +checkLimit(String key, IRateLimitStrategy strategy) boolean
    }

    class ExternalServiceCaller {
        -RateLimitManager rateLimitManager
        +processRequest(String clientId, boolean needsExternalCall)
        -callExternalResource()
    }

    IRateLimitStrategy <|.. FixedWindowStrategy : Realization
    IRateLimitStrategy <|.. SlidingWindowStrategy : Realization
    RateLimitManager --> IRateLimitStrategy : Depends on
    ExternalServiceCaller --> RateLimitManager : Uses
