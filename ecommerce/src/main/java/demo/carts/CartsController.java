package demo.carts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.carts.dtos.CustomerCartItem;
import demo.carts.models.Cart;
import demo.carts.models.CartItem;
import demo.products.ProductsSpecs;
import demo.products.dtos.ProductDetail;
import demo.products.models.Product;
import demo.products.repositories.ProductRepository;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/carts")
public class CartsController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartsController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public Cart createCart(@Valid @RequestBody List<CustomerCartItem> items) {
        final Map<Long, CustomerCartItem> cartItemTable = items.stream()
                .collect(Collectors.toMap(ci -> ci.getProductId(), ci -> ci));

        final Set<Long> productIds = cartItemTable.keySet();

        final Specification<Product> productSpecs = Specification
                .where(ProductsSpecs.getProductsIn(productIds))
                .and(ProductsSpecs.fetchBrandUsingJoinStatement());

        final List<ProductDetail> products = productRepository.findBy(productSpecs, fluentQuery -> {
            return fluentQuery.as(ProductDetail.class).all();
        });

        final List<CartItem> cartItems = new ArrayList<>();

        for (final ProductDetail product : products) {
            cartItems.add(CartItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(cartItemTable.get(product.getId()).getQuantity())
                    .price(product.getPrice())
                    .productImageUrl(product.getImageUrl())
                    .productBrand(product.getBrand())
                    .build());
        }

        Cart cart = new Cart(cartItems);
        System.out.println(cart);
        cart = cartRepository.save(cart);
        return cart;
    }

}
