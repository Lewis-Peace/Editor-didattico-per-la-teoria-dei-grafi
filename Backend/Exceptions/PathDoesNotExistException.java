package Backend.Exceptions;

public class PathDoesNotExistException extends Exception{
    
    /**
     *
     */
    private static final long serialVersionUID = -7664119835424202114L;
    private String error;
    public PathDoesNotExistException (String error) {
        this.error = error;
    }

    public String toString() {
        return "This object can't return the specified path caused of " + error;
    }
}
