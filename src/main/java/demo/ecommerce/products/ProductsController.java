package demo.ecommerce.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/v1/products")
public class ProductsController {

    @GetMapping()
    public String getPageProduct() {
        return new String("Hello World");
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") Long id) {
        return new String("" + id);
    }

    @GetMapping("/brands")
    public String getProductsBrands() {
        return new String("Product Brands here");
    }

}
