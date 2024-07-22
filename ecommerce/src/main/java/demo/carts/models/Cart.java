package demo.carts.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@RedisHash("ecommerce.carts")
public class Cart implements Serializable {
    public Cart() {
        id = UUID.randomUUID().toString();
    }

    public Cart(List<CartItem> items) {
        this();
        this.items = items;
    }

    @Id
    private String id;
    @Setter
    private List<CartItem> items;
}
