package demo.ecommerce.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.products.models.ProductBrand;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

}
