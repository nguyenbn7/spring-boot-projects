package demo.ecommerce.application.products;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.ecommerce.application.products.models.Product;
import demo.ecommerce.application.products.models.ProductBrand;
import demo.ecommerce.application.products.repositories.ProductBrandRepository;
import demo.ecommerce.application.products.repositories.ProductRepository;

@Configuration
public class SeedDemoData {

    @Bean
    @Order(1)
    CommandLineRunner seedProductBrand(ProductBrandRepository productBrandRepository) {
        return args -> {
            if (productBrandRepository.count() > 0) {
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<ProductBrand>> typeReference = new TypeReference<List<ProductBrand>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/demodata/products/brands.json");

            try {
                List<ProductBrand> brands = mapper.readValue(inputStream, typeReference);
                productBrandRepository.saveAll(brands);
                System.out.println("Seed product brand data successful");
            } catch (IOException e) {
                System.out.println("Unable to seed product brand data: " + e.getMessage());
            }
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner seedProduct(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) {
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/demodata/products/products.json");

            try {
                List<Product> products = mapper.readValue(inputStream, typeReference);
                productRepository.saveAll(products);
                System.out.println("Seed product data successful");
            } catch (IOException e) {
                System.out.println("Unable to seed product data: " + e.getMessage());
            }
        };
    }
}
