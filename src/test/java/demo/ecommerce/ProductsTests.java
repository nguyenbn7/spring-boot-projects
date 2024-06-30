package demo.ecommerce;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

import demo.ecommerce.application.products.ProductsController;
import demo.ecommerce.application.products.dtos.ProductDetail;
import demo.ecommerce.application.products.models.Product;
import demo.ecommerce.application.products.repositories.ProductBrandRepository;
import demo.ecommerce.application.products.repositories.ProductRepository;

class ProductDetailMockObj implements ProductDetail {

    private Long id = 1L;
    private String name = "name";
    private BigDecimal price = new BigDecimal(1);
    private String imageUrl = "imageUrl";
    private String description = "description";
    private String brand = "brand";

    public ProductDetailMockObj() {
    }

    public ProductDetailMockObj(
            Long id, String name, BigDecimal price,
            String imageUrl, String description, String brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.brand = brand;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getBrand() {
        return brand;
    }

}

@DisabledInAotMode
@WebMvcTest(ProductsController.class)
public class ProductsTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductBrandRepository productBrandRepository;

    @Test
    void contextLoads() {
        assertThat(productRepository).isNotNull();
        assertThat(productBrandRepository).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    void getProductDetail_returnData() throws Exception {
        when(productRepository.findBy((Specification<Product>) Mockito.isNotNull(),
                (Function<FetchableFluentQuery<Product>, Optional<ProductDetail>>) Mockito.isNotNull()))
                .thenReturn(Optional.of(new ProductDetailMockObj()));

        this.mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @SuppressWarnings("unchecked")
    @Test
    void getProductDetail_returnNotFound() throws Exception {
        when(productRepository.findBy((Specification<Product>) Mockito.isNotNull(),
                (Function<FetchableFluentQuery<Product>, Optional<ProductDetail>>) Mockito.isNotNull()))
                .thenReturn(Optional.ofNullable(null));

        this.mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
