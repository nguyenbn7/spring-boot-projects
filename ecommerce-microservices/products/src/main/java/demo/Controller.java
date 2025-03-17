package demo.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/products")
public class Controller {
    @GetMapping()
    public String getPageProduct() {
        return new String("Hello World!");
    }

}
