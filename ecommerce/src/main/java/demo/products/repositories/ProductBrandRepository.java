package demo.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.products.models.ProductBrand;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

}
