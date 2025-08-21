package com.ecommerce.productService.infrastructure.persistence.entity;

import com.ecommerce.productService.domain.model.constant.MovementType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MovementType type; // IN, OUT, ADJUST

    @Column(nullable = false)
    private Integer quantity;

    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // FIXME: Agregar movement_date y cambiar note a reference

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
