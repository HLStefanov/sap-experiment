package validator;




import java.util.List;

public class Validator {

    /**            This method checks if the fields, representing the indexes in L-Switch are empty
     * @param args is an array of the indexes of the lines an words of a file loaded in L-Switch
     * @throws NumericInputException if at least one the fields is empty
     */
    public static void validateNumericInput(String...args) throws NumericInputException {
        for (String elem: args) {
            if(elem.isEmpty()){
                throw new NumericInputException();
            }
        }

    }

    /**            This method checks if the list of lists of words in L-Switch is empty
     * @param list represents a list of lists, which represent the lines of the file loaded in L-Switch
     * @throws EmptyListException if the list is empty
     */
    public static void validateListOfLists(List<List<String>> list) throws EmptyListException {
        if(list.isEmpty()){
            throw new EmptyListException();
        }
    }

    /**            This method checks if the list is empty
     * @param list This list of strings is used in L-Switch as a representation of the lines in the loaded file
     * @throws EmptyListException if list is empty
     */
    public static void validateList(List<String> list) throws EmptyListException{
        if(list.isEmpty()){
            throw new EmptyListException();
        }
    }
    /**                 This method checks if the two indexes, entered for two elements of a list, are in the list
     * @param  args     array of Integer objects, whose elements [0] and [1] represent the indexes
     *                  of two elements in a list, which has been filled with the lines of the file,
     *                  loaded in the L-Switch app
     * @param  arrayList list of lists of strings, which strings, represent words
     * @throws InvalidIndexException if input index is found to be out of bounds
     */
    public static void validateListIndexes(List <String> arrayList, Integer ... args)throws InvalidIndexException {
        if(!inList(arrayList,args[0]) || !inList(arrayList,args[1])) {
            throw new InvalidIndexException();
        }

    }

    /**                 This method checks if the four indexes entered for two elements of a two lists of strings
     *                  are in bounds
     * @param  args     array of Integer objects, whose elements [0] and [2] represent
     *                  the indexes of the lists, contained in the arrayList object.
     *                  [1] and [3] represent the indexes of the searched strings
     *                  [0] corresponds to [1] and [2] corresponds to [3]
     * @param  arrayList list of lists of strings, which strings, represent words
     * @throws InvalidIndexException if input index is found to be out of bounds
     */
    public static void validateWordsIndexes(List <List<String>> arrayList, Integer ... args)throws InvalidIndexException{
        if(!inListOfLists(arrayList,args[0]) || !inListOfLists(arrayList,args[1])) {
            throw new InvalidIndexException();
        }else if(!inList(arrayList.get(args[0]-1),args[2]) || !inList(arrayList.get(args[1]-1),args[3])){
            throw new InvalidIndexException();
        }

    }

    /**
     * @param  args  a list of String objects, containing either words or lines
     * @param  index index of the argument to be checked
     * @return      true if the index is int list, false otherwise
     */
    private static boolean inList(List<String> args, int index){
        return index <= args.size() && index >= 1;
    }

    /**
     * @param  args  a list of List<String> objects, containing words
     * @param  index index of the argument to be checked
     * @return      true if the index is int list, false otherwise
     */
    private static boolean inListOfLists(List<List<String>> args,int index){
        return index <= args.size() && index >= 1;
    }

}