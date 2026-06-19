# Phase 5 - Fault Tolerance

> 📚 **Course**: [Spring Batch 6.x Complete Learning Course](../README.md)  
> ⬅️ **Previous**: [Phase 4 - Validation](./Phase-4-Validation.md)

## Overview

Build resilient batch jobs that can handle failures gracefully. Learn to implement Skip and Retry strategies to recover from transient errors and skip permanently bad data.

## Key Concepts
- Skip
- Retry
- SkipLimit
- RetryLimit

## Example
```java
.faultTolerant()
.skip(Exception.class)
.skipLimit(10)
.retry(SQLException.class)
.retryLimit(3)
```

## Best Practices
- Retry transient failures only.

## Common Pitfalls
- Infinite retry patterns.

## Testing Strategies
- Forced failures.
- Retry verification.

## Production Considerations
- Monitoring skipped records.

## References
- Spring Batch Fault Tolerance

---

## 🔗 Navigation

**Course Index**: [← Back to Course](../README.md)

**Previous Phase**: [← Phase 4 - Validation](./Phase-4-Validation.md)  
**Next Phase**: [Phase 6 - Listeners →](./Phase-6-Listeners.md)

**Related Phases**:
- [Phase 2 - Chunk Processing Fundamentals](./Phase-2-Chunk-Processing-Fundamentals.md) - Chunk boundaries and transaction control
- [Phase 4 - Validation](./Phase-4-Validation.md) - Prevent invalid data before fault tolerance
- [Phase 6 - Listeners](./Phase-6-Listeners.md) - Monitor skipped and retried items

**Quick Links**: [All Phases](../README.md#quick-navigation)

