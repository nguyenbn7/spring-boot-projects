package demo.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.ecommerce.application.products.models.Product;
import demo.ecommerce.application.products.models.ProductBrand;
import demo.ecommerce.application.products.repositories.ProductBrandRepository;
import demo.ecommerce.application.products.repositories.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisabledInAotMode
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Profile("test")
@AutoConfigureMockMvc
public class ProductsTests {
    private final long PRODUCT_BRAND_TOTAL_ROWS = 5;
    private final long PRODUCT_TOTAL_ROWS = 10;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @BeforeAll
    @Order(1)
    void loadProductBrandData() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ProductBrand>> typeReference = new TypeReference<List<ProductBrand>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/testdata/products/brands.json");

        try {
            List<ProductBrand> brands = mapper.readValue(inputStream, typeReference);
            productBrandRepository.saveAll(brands);
            System.out.println("Load product brand test data successfully");
        } catch (IOException e) {
            System.out.println("Unable to load product brand test data: " + e.getMessage());
        }
    }

    @BeforeAll
    @Order(2)
    void loadProductData() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/testdata/products/products.json");

        try {
            List<Product> products = mapper.readValue(inputStream, typeReference);
            productRepository.saveAll(products);
            System.out.println("Load product test data successfully");
        } catch (IOException e) {
            System.out.println("Unable to load product test data: " + e.getMessage());
        }
    }

    @Test
    void checkIfDataLoaded() {
        assertThat(productBrandRepository.count()).isEqualTo(PRODUCT_BRAND_TOTAL_ROWS);
        assertThat(productRepository.count()).isEqualTo(PRODUCT_TOTAL_ROWS);
    }

    @Test
    void get_givenDefault_returnProductBrands() throws Exception {
        this.mockMvc.perform(get("/v1/products/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void get_givenProductIdInData_returnProductDetailWithStatusOk() throws Exception {
        this.mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").isString());
    }

    @Test
    void get_givenProductIdNotInData_returnStatusNotFound() throws Exception {
        this.mockMvc.perform(get("/v1/products/" + (PRODUCT_TOTAL_ROWS + 1)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
