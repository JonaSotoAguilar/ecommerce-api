package com.ecommerce.productservice.domain.event;

import java.time.OffsetDateTime;

public interface DomainEvent {
    OffsetDateTime occurredAt();
}