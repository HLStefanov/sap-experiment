package validator;

public class InvalidIndexException extends Exception{

    @Override
    public String getMessage(){
        return "PLEASE ENTER VALID LINE INDEX BETWEEN 1 AND ";
    }
}
