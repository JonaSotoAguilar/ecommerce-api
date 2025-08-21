package com.ecommerce.productService.domain.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementDto {
    private Long id;
    private String type;
    private Integer quantity;
    private String note;
    private String createdAt;
    private Long productId;

    // FIXME: Cambiar createdAt por movement_date y note a reference
}
