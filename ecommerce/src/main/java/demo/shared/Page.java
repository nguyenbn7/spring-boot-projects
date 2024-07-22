package demo.shared;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Page<T> {
    private int pageNumber;
    private int pageSize;
    private long totalItems;
    private List<T> data;
}
