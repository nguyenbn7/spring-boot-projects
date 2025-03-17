package demo.carts;

import org.springframework.data.repository.CrudRepository;

import demo.carts.models.Cart;

public interface CartRepository extends CrudRepository<Cart, String> {

}
