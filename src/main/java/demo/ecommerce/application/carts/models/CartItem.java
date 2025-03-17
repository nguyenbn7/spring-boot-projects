package demo.ecommerce.application.carts.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private Long productId;
    private BigDecimal price;
    private Integer quantity;
}
