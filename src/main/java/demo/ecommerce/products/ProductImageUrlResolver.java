package demo.ecommerce.products;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductImageUrlResolver {
    @Value("${app.image-base-url}")
    private String baseImageUrl;

    public String getFullImageUrl(String imageUrl) throws URISyntaxException {
        String baseUrl = baseImageUrl;

        if (!baseImageUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        return new URI(baseUrl).resolve(imageUrl).toString();
    }
}
