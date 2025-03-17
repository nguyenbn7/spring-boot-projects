package demo.ecommerce.products;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SeedDemoData {

    @Bean
    CommandLineRunner runner(ProductBrandRepository productBrandRepository, ProductRepository productRepository) {
        return args -> {
            if (productBrandRepository.count() > 0) {
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<ProductBrand>> typeReference = new TypeReference<List<ProductBrand>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/brands.json");

            try {
                List<ProductBrand> brands = mapper.readValue(inputStream, typeReference);
                productBrandRepository.saveAll(brands);
                System.out.println("Product Brands Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save Product Brands: " + e.getMessage());
            }
        };
    }
}
