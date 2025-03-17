package demo.ecommerce.application.carts.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCartItem {
    @Min(1)
    private Long productId;
    @Min(1)
    private Integer quantity;
}
