package demo.ecommerce.application.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.application.products.models.ProductBrand;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

}
