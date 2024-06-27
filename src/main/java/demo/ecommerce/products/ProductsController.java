package demo.ecommerce.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ecommerce.products.dtos.ProductDetail;
import demo.ecommerce.products.dtos.ProductsQueries;
import demo.ecommerce.products.entities.Product;
import demo.ecommerce.products.entities.ProductBrand;
import demo.ecommerce.products.repositories.ProductBrandRepository;
import demo.ecommerce.products.repositories.ProductRepository;
import demo.ecommerce.shared.Page;
import demo.ecommerce.shared.error.NotFoundEntityException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<Product> getPageProduct(ProductsQueries queries) {
        Optional<Specification<Product>> specsOptional = Optional.empty();
        Pageable pageable = PageRequest.of(queries.getPageNumber() - 1, queries.getPageSize());

        if (queries.getBrandId().isPresent()) {
            specsOptional = Optional.of(
                    Specification.where(
                            ProductsSpecs.matchBrand(
                                    queries.getBrandId().get())));
        }

        if (specsOptional.isPresent()) {
            var pageProduct = productRepository.findAll(specsOptional.get(), pageable);
            return Page.<Product>builder()
                    .pageNumber(queries.getPageNumber())
                    .pageSize(queries.getPageSize())
                    .totalItems(pageProduct.getTotalElements())
                    .data(pageProduct.getContent())
                    .build();
        }

        var pageProduct = productRepository.findAll(pageable);
        return Page.<Product>builder()
                .pageNumber(queries.getPageNumber())
                .pageSize(queries.getPageSize())
                .totalItems(pageProduct.getTotalElements())
                .data(pageProduct.getContent())
                .build();
    }

    @GetMapping("/{id}")
    public ProductDetail getProduct(@PathVariable("id") Long id) {
        var optional = productRepository.findById(id, ProductDetail.class);

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
