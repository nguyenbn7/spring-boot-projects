package demo.ecommerce.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ecommerce.products.dtos.ProductDetail;
import demo.ecommerce.products.dtos.ProductsQueries;
import demo.ecommerce.products.dtos.ShopProduct;
import demo.ecommerce.products.models.Product;
import demo.ecommerce.products.models.ProductBrand;
import demo.ecommerce.products.repositories.ProductBrandRepository;
import demo.ecommerce.products.repositories.ProductRepository;
import demo.ecommerce.shared.Page;
import demo.ecommerce.shared.error.NotFoundEntityException;

import java.util.List;
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

    @GetMapping()
    public Page<ShopProduct> getPageProduct(ProductsQueries queries) {
        final Sort sortBy = switch (queries.getSort().toLowerCase()) {
            case "price" -> Sort.by("price");
            case "-price" -> Sort.by(Direction.DESC, "price");
            default -> Sort.by("name");
        };

        Specification<Product> specs = Specification.where(null);

        if (queries.getBrandId().isPresent()) {
            specs = specs.and(ProductsSpecs.matchBrand(queries.getBrandId().get()));
        }

        Pageable pageable = PageRequest.of(queries.getPageNumber() - 1, queries.getPageSize());

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
