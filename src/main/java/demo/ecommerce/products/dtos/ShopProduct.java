package demo.ecommerce.products.dtos;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;

public interface ShopProduct {
    Long getId();

    String getName();

    BigDecimal getPrice();

    @Value("#{@productImageUrlResolver.getFullImageUrl(target.imageUrl)}")
    String getImageUrl();
}
