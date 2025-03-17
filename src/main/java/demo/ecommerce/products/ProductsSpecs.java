package demo.ecommerce.products;

import org.springframework.data.jpa.domain.Specification;

import demo.ecommerce.products.models.Product;

public class ProductsSpecs {
    public static Specification<Product> matchBrand(Long brandId) {
        return (root, query, builder) -> {
            return builder.equal(root.get("brandId"), brandId);
        };
    }
}
