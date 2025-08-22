package com.ecommerce.productService.domain.model.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementDto {
    private Long id;
    private Long productId;
    private String type;
    private Integer quantity;
    private String reference;
    private OffsetDateTime movementDate;
}
