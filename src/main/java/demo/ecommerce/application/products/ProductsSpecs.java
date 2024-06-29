package demo.ecommerce.application.products;

import org.springframework.data.jpa.domain.Specification;

import demo.ecommerce.application.products.models.Product;
import jakarta.persistence.criteria.JoinType;

public class ProductsSpecs {
    public static Specification<Product> matchBrand(Long brandId) {
        return (root, query, builder) -> {
            return builder.equal(root.get("brandId"), brandId);
        };
    }

    public static Specification<Product> fetchBrandUsingJoin() {
        return (root, query, builder) -> {
            root.fetch("brand", JoinType.INNER);
            return null;
        };
    }

    public static Specification<Product> getProductBy(Long id) {
        return (root, query, builder) -> {
            return builder.equal(root.get("id"), id);
        };
    }
}
