package demo.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

import demo.ecommerce.application.carts.CartRepository;

@Profile("test")
@DisabledInAotMode
@SpringBootTest(classes = { demo.ecommerce.EcommerceApplication.class })
@AutoConfigureMockMvc
public class CartsTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void post_givenCartItem_returnCreatedCart() {

    }
}
