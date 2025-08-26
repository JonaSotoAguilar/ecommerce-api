package com.ecommerce.productService.domain.event;

import java.time.OffsetDateTime;

public interface DomainEvent {
    OffsetDateTime occurredAt();
}