package demo.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.products.dtos.ProductDetail;
import demo.products.dtos.ProductsQueries;
import demo.products.dtos.ShopProduct;
import demo.products.models.Product;
import demo.products.models.ProductBrand;
import demo.products.repositories.ProductBrandRepository;
import demo.products.repositories.ProductRepository;
import demo.shared.Page;
import demo.shared.error.NotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    @GetMapping
    public Page<ShopProduct> getPageProduct(ProductsQueries queries) {
        final Sort sortBy = switch (queries.getSort().toLowerCase()) {
            case "price" -> Sort.by("price");
            case "-price" -> Sort.by(Direction.DESC, "price");
            default -> Sort.by("name");
        };

        final Specification<Product> specs = Specification
                .where(queries.getBrandId().isPresent() ? ProductsSpecs.matchBrand(queries.getBrandId().get()) : null);

        final Pageable pageable = PageRequest.of(queries.getPageNumber() - 1, queries.getPageSize());

        var pageProduct = productRepository.findBy(specs, fluentQuery -> {
            return fluentQuery
                    .sortBy(sortBy)
                    .as(ShopProduct.class)
                    .page(pageable);
        });

        return Page.<ShopProduct>builder()
                .pageNumber(queries.getPageNumber())
                .pageSize(queries.getPageSize())
                .totalItems(pageProduct.getTotalElements())
                .data(pageProduct.getContent())
                .build();
    }

    @GetMapping("/{id}")
    public ProductDetail getProduct(@PathVariable("id") Long id) {
        final Specification<Product> specs = ProductsSpecs
                .fetchBrandUsingJoinStatement()
                .and(ProductsSpecs.getProductBy(id));

        final Optional<ProductDetail> optional = productRepository.findBy(specs, fluentQuery -> {
            return fluentQuery.as(ProductDetail.class).first();
        });

        if (optional.isEmpty())
            throw new NotFoundException("Product does not exist");

        return optional.get();
    }

    @GetMapping("/brands")
    public List<ProductBrand> getProductsBrands() {
        return productBrandRepository.findAll();
    }

}
