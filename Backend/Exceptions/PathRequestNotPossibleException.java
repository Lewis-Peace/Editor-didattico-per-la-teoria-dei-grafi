package Backend.Exceptions;

public class PathRequestNotPossibleException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 4416886149547020394L;
    int typeOfError;

    public PathRequestNotPossibleException(int errorType) {
        typeOfError = errorType;
    }

    public String toString() {
        switch (typeOfError) {
            case 0:
            return errorInTheLengthInput();
            case 1:
            return doesNotExistAnyPathOfThisLength();
            default:
            return "Error to define"; 
        }
    }

    private String doesNotExistAnyPathOfThisLength() {
        return "In this graph does not exist a path this long";
    }

    private String errorInTheLengthInput() {
        return "The only length accepted are 2 or more";
    }
    
}
