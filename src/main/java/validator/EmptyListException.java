package validator;

public class EmptyListException extends Exception {

    @Override
    public String getMessage(){
        return "YOUR LIST IS EMPTY. PLEASE LOAD A FILE!";
    }
}
