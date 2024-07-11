package exeptions;

public class NoValidMoveLeftException extends IllegalStateException{
    public NoValidMoveLeftException() {
        super("No valid move left");
    }
}
