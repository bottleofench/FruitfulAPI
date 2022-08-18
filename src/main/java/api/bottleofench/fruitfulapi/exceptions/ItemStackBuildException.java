package api.bottleofench.fruitfulapi.exceptions;

public class ItemStackBuildException extends RuntimeException {
    public ItemStackBuildException(String reason) {
        super(reason);
    }
}
