package demo.ecommerce.products.dtos;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@EqualsAndHashCode
public class ProductsQueries {
    private final int MAX_PAGE_SIZE = 500;
    private final int DEFAULT_PAGE_SIZE = 5;

    private Long brandId;
    private Integer pageSize;
    private Integer pageNumber;

    public Optional<Long> getBrandId() {
        return Optional.ofNullable(brandId);
    }

    public int getPageSize() {
        int pageSizeValue = Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE);
        return pageSizeValue < 1 ? DEFAULT_PAGE_SIZE : pageSizeValue > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSizeValue;
    }

    public int getPageNumber() {
        int pageNumberValue = Optional.ofNullable(pageNumber).orElse(1);
        return pageNumberValue < 1 ? 1 : pageNumberValue;
    }
}
