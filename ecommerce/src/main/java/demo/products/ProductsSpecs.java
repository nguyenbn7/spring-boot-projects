package demo.products;

import java.util.Collection;
import org.springframework.data.jpa.domain.Specification;

import demo.products.models.Product;
import jakarta.persistence.criteria.JoinType;

public class ProductsSpecs {
    public static Specification<Product> matchBrand(Long brandId) {
        return (root, query, builder) -> {
            return builder.equal(root.get("brandId"), brandId);
        };
    }

    public static Specification<Product> fetchBrandUsingJoinStatement() {
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

    public static Specification<Product> getProductsIn(Collection<Long> ids) {
        return (root, query, builder) -> {
            return root.<Long>get("id").in(ids);
        };
    }
}
