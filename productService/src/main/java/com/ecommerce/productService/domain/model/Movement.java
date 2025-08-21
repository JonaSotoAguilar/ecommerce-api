package com.ecommerce.productService.domain.model;


import com.ecommerce.productService.domain.model.constant.MovementType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    private Long id;
    private MovementType type;
    private Integer quantity;
    private String note;
    private LocalDateTime createdAt;
    private Product product;

    //FIXME: Cambiar createdAt por movement_date Y nota a reference
}
