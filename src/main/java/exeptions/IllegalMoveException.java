package exeptions;

public class IllegalMoveException extends IllegalArgumentException{
    public IllegalMoveException(String message) {
        super(message);
    }
}
