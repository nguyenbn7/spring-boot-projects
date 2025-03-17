package demo.ecommerce.application.carts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ecommerce.application.carts.dtos.CustomerCartItem;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/carts")
public class CartsController {

    @PostMapping
    public String createCart(@Valid @RequestBody List<CustomerCartItem> items) {
        items.forEach(System.out::println);
        return "OK";
    }

}
