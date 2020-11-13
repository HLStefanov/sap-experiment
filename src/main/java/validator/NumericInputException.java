package validator;

public class NumericInputException extends Exception{

    @Override
    public String getMessage(){
        return "PLEASE ENTER NUMERIC INPUT";
    }
}
