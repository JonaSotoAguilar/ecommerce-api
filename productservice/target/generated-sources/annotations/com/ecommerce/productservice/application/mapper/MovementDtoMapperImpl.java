package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.MovementDto;
import com.ecommerce.productservice.domain.event.StockAdjustedEvent;
import com.ecommerce.productservice.domain.model.movement.Movement;
import com.ecommerce.productservice.domain.model.movement.MovementType;
import com.ecommerce.productservice.domain.model.product.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:16:27-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class MovementDtoMapperImpl implements MovementDtoMapper {

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Override
    public MovementDto toDto(Movement movement) {
        if ( movement == null ) {
            return null;
        }

        Long productId = null;
        Long id = null;
        MovementType type = null;
        Integer stockBefore = null;
        Integer quantity = null;
        BigDecimal averageCostBefore = null;
        BigDecimal unitCost = null;
        String reference = null;
        String productNameSnapshot = null;
        LocalDateTime movementDate = null;

        productId = movementProductId( movement );
        id = movement.id();
        type = movement.type();
        stockBefore = movement.stockBefore();
        quantity = movement.quantity();
        averageCostBefore = movement.averageCostBefore();
        unitCost = movement.unitCost();
        reference = movement.reference();
        productNameSnapshot = movement.productNameSnapshot();
        movementDate = movement.movementDate();

        MovementDto movementDto = new MovementDto( id, type, stockBefore, quantity, averageCostBefore, unitCost, reference, productNameSnapshot, productId, movementDate );

        return movementDto;
    }

    @Override
    public List<MovementDto> toDtoList(List<Movement> movements) {
        if ( movements == null ) {
            return null;
        }

        List<MovementDto> list = new ArrayList<MovementDto>( movements.size() );
        for ( Movement movement : movements ) {
            list.add( toDto( movement ) );
        }

        return list;
    }

    @Override
    public Movement toDomain(StockAdjustedEvent event) {
        if ( event == null ) {
            return null;
        }

        Product product = null;
        MovementType type = null;
        Integer stockBefore = null;
        Integer quantity = null;
        BigDecimal averageCostBefore = null;
        BigDecimal unitCost = null;
        String reference = null;
        String productNameSnapshot = null;

        product = productDtoMapper.map( event.productId() );
        type = event.type();
        stockBefore = event.stockBefore();
        quantity = event.quantity();
        averageCostBefore = event.averageCostBefore();
        unitCost = event.unitCost();
        reference = event.reference();
        productNameSnapshot = event.productNameSnapshot();

        Long id = null;
        LocalDateTime movementDate = null;

        Movement movement = new Movement( id, type, stockBefore, quantity, averageCostBefore, unitCost, reference, productNameSnapshot, movementDate, product );

        return movement;
    }

    private Long movementProductId(Movement movement) {
        Product product = movement.product();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }
}
