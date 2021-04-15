package au.com.tava.Core.CustomExceptions;

public class NoSuchKeyException extends RuntimeException {

    public NoSuchKeyException( String message ){
        super ( " Key " + message + " not found in this session" );
    }
}
