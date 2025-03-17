package demo.ecommerce.products.dtos;

import java.math.BigDecimal;

public interface ShopProduct {
    Long getId();

    String getName();

    BigDecimal getPrice();

    String getImageUrl();
}
