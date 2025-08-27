package com.ecommerce.productservice.infrastructure.persistence.entity;

import com.ecommerce.productservice.domain.model.movement.MovementType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movement",
        indexes = @Index(name = "idx_movement_product", columnList = "product_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @Column(name = "stock_before", nullable = false)
    private Integer stockBefore;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "average_cost_before", nullable = false, precision = 19, scale = 4)
    private BigDecimal averageCostBefore;

    @Column(name = "unit_cost", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitCost;

    private String reference;

    @Column(name = "product_name_snapshot")
    private String productNameSnapshot;

    // --- Product relationship ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductEntity product;

    // --- audit ---
    @CreatedDate
    @Column(name = "movement_date", updatable = false)
    private LocalDateTime movementDate;
}
