package com.ecommerce.productservice.infrastructure.persistence.mapper;

import com.ecommerce.productservice.domain.model.movement.Movement;
import com.ecommerce.productservice.domain.model.movement.MovementType;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.infrastructure.persistence.entity.MovementEntity;
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
public class MovementDboMapperImpl implements MovementDboMapper {

    @Autowired
    private ProductDboMapper productDboMapper;

    @Override
    public MovementEntity toDbo(Movement domain) {
        if ( domain == null ) {
            return null;
        }

        MovementEntity.MovementEntityBuilder movementEntity = MovementEntity.builder();

        movementEntity.id( domain.id() );
        movementEntity.type( domain.type() );
        movementEntity.stockBefore( domain.stockBefore() );
        movementEntity.quantity( domain.quantity() );
        movementEntity.averageCostBefore( domain.averageCostBefore() );
        movementEntity.unitCost( domain.unitCost() );
        movementEntity.reference( domain.reference() );
        movementEntity.productNameSnapshot( domain.productNameSnapshot() );
        movementEntity.product( productDboMapper.toDbo( domain.product() ) );
        movementEntity.movementDate( domain.movementDate() );

        return movementEntity.build();
    }

    @Override
    public Movement toDomain(MovementEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        MovementType type = null;
        Integer stockBefore = null;
        Integer quantity = null;
        BigDecimal averageCostBefore = null;
        BigDecimal unitCost = null;
        String reference = null;
        String productNameSnapshot = null;
        LocalDateTime movementDate = null;
        Product product = null;

        id = entity.getId();
        type = entity.getType();
        stockBefore = entity.getStockBefore();
        quantity = entity.getQuantity();
        averageCostBefore = entity.getAverageCostBefore();
        unitCost = entity.getUnitCost();
        reference = entity.getReference();
        productNameSnapshot = entity.getProductNameSnapshot();
        movementDate = entity.getMovementDate();
        product = productDboMapper.toDomain( entity.getProduct() );

        Movement movement = new Movement( id, type, stockBefore, quantity, averageCostBefore, unitCost, reference, productNameSnapshot, movementDate, product );

        return movement;
    }

    @Override
    public List<Movement> toDomainList(List<MovementEntity> movements) {
        if ( movements == null ) {
            return null;
        }

        List<Movement> list = new ArrayList<Movement>( movements.size() );
        for ( MovementEntity movementEntity : movements ) {
            list.add( toDomain( movementEntity ) );
        }

        return list;
    }
}
