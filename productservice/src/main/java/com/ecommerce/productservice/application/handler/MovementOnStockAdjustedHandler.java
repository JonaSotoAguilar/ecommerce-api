package com.ecommerce.productservice.application.handler;

import com.ecommerce.productservice.application.mapper.MovementDtoMapper;
import com.ecommerce.productservice.domain.event.StockAdjustedEvent;
import com.ecommerce.productservice.domain.port.MovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MovementOnStockAdjustedHandler {
    private final MovementRepositoryPort repo;
    private final MovementDtoMapper mapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void on(StockAdjustedEvent event) {
        repo.save(mapper.toDomain(event));
    }
}

