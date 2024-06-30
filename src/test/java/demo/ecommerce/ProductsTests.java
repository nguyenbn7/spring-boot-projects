package demo.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import demo.ecommerce.application.products.repositories.ProductBrandRepository;
import demo.ecommerce.application.products.repositories.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Profile("test")
@DisabledInAotMode
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = { demo.ecommerce.EcommerceApplication.class })
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:product_brand_test_data.sql",
        "classpath:product_test_data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
public class ProductsTests {
    private final long PRODUCT_BRAND_TOTAL_ROWS = 5;
    private final long PRODUCT_TOTAL_ROWS = 10;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Test
    void checkIfDataLoaded() {
        assertThat(productBrandRepository.count()).isEqualTo(PRODUCT_BRAND_TOTAL_ROWS);
        assertThat(productRepository.count()).isEqualTo(PRODUCT_TOTAL_ROWS);
    }

    @Test
    void get_givenDefault_returnPageProduct() throws Exception {
        this.mockMvc.perform(get("/v1/products"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.pageNumber", is(1)),
                        jsonPath("$.pageSize", is(5)),
                        jsonPath("$.totalItems", is(10)),
                        jsonPath("$.data", hasSize(5)));
    }

    @Test
    void get_givenDefault_returnProductBrands() throws Exception {
        this.mockMvc.perform(get("/v1/products/brands"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(5)));
    }

    @Test
    void get_givenBrandId_returnPageProduct() throws Exception {
        this.mockMvc.perform(get("/v1/products?brandId=" + 5))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.pageNumber", is(1)),
                        jsonPath("$.pageSize", is(5)),
                        jsonPath("$.totalItems", is(1)),
                        jsonPath("$.data", hasSize(1)));
    }

    @Test
    void get_givenProductIdInData_returnProductDetailWithStatusOk() throws Exception {
        this.mockMvc.perform(get("/v1/products/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.name").isString());
    }

    @Test
    void get_givenProductIdNotInData_returnStatusNotFound() throws Exception {
        this.mockMvc.perform(get("/v1/products/" + (PRODUCT_TOTAL_ROWS + 1)))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }
}
