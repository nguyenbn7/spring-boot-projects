package demo.ecommerce.products.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import demo.ecommerce.products.dtos.ShopProduct;
import demo.ecommerce.products.entities.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    <T> Optional<T> findById(Long id, Class<T> type);

    Page<ShopProduct> findAllProjectedBy(Pageable pageable);
}
