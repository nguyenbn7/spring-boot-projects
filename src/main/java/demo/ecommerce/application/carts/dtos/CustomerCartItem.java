package demo.ecommerce.application.carts.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CustomerCartItem {
    @Min(1)
    private Long productId;
    @Min(1)
    private Integer quantity;
}
