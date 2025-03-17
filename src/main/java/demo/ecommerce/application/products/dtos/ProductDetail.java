package demo.ecommerce.application.products.dtos;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

public interface ProductDetail {
    Long getId();

    String getName();

    BigDecimal getPrice();

    @Value("#{@productImageUrlResolver.getFullImageUrl(target.imageUrl)}")
    String getImageUrl();

    String getDescription();

    @Value("#{target.brand.name}")
    String getBrand();
}
