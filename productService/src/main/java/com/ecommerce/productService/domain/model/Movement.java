package com.ecommerce.productService.domain.model;


import com.ecommerce.productService.domain.model.constant.MovementType;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    private Long id;
    private MovementType type;
    private Integer quantity;
    private String reference;
    private OffsetDateTime movementDate;
    private Product product;
}
