package demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.carts.CartRepository;
import demo.carts.dtos.CustomerCartItem;
import demo.carts.models.Cart;
import demo.products.repositories.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@Profile("test")
@DisabledInAotMode
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = { demo.EcommerceApplication.class })
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:product_brand_test_data.sql",
        "classpath:product_test_data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class CartsTests {
    private final long PRODUCT_TOTAL_ROWS = 10;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private CartRepository cartRepository;

    @Test
    void checkIfDataLoaded() {
        assertThat(productRepository.count()).isEqualTo(PRODUCT_TOTAL_ROWS);
    }

    @Test
    void post_givenCartItem_returnCreatedCart() throws Exception {
        when(cartRepository.save(any(Cart.class))).then(i -> i.getArguments()[0]);

        List<CustomerCartItem> items = new ArrayList<>();
        items.add(new CustomerCartItem(3L, 1));
        final ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/v1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(items)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.items", hasSize(1)),
                        jsonPath("$.items[0].productId", is(3)),
                        jsonPath("$.items[0].productName").isNotEmpty());
    }
}
