package demo.ecommerce.shared.error;

public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException() {
        super();
    }

    public NotFoundEntityException(String message) {
        super(message);
    }
}
