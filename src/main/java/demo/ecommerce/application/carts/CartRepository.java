package demo.ecommerce.application.carts;

import org.springframework.data.repository.CrudRepository;

import demo.ecommerce.application.carts.models.Cart;

public interface CartRepository extends CrudRepository<Cart, String> {

}
