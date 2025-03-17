package demo.ecommerce.products.dtos;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class ProductImageUrlResolver {
    @Value("${app.image-base-url}")
    private String baseImageUrl;

    public String getFullImageUrl(String imageUrl) {
        String result = baseImageUrl;

        if (!baseImageUrl.endsWith("/")) {
            result = result + "/";
        }

        return result + imageUrl;
    }
}

public interface ShopProduct {
    Long getId();

    String getName();

    BigDecimal getPrice();

    @Value("#{@productImageUrlResolver.getFullImageUrl(target.imageUrl)}")
    String getImageUrl();
}
