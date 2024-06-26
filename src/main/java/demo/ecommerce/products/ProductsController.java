package demo.ecommerce.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ecommerce.shared.error.NotFoundEntityException;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/v1/products")
public class ProductsController {
    private final ProductRepository productRepository;
    private final ProductBrandRepository productBrandRepository;

    public ProductsController(
            ProductRepository productRepository,
            ProductBrandRepository productBrandRepository) {
        this.productRepository = productRepository;
        this.productBrandRepository = productBrandRepository;
    }

    @GetMapping()
    public List<Product> getPageProduct() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundEntityException("Product does not exist");
        }

        return optional.get();
    }

    @GetMapping("/brands")
    public List<ProductBrand> getProductsBrands() {
        return productBrandRepository.findAll();
    }

}
