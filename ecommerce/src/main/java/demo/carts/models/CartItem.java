package demo.carts.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
@Getter
public class CartItem implements Serializable {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private String productImageUrl;
    private String productBrand;
    private Integer quantity;
}
