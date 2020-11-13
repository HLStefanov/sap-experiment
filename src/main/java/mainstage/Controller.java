package mainstage;



import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import validator.EmptyListException;
import validator.InvalidIndexException;
import validator.NumericInputException;
import validator.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    @FXML
    private Button computeWords;

    @FXML
    private Label secLabel;

    @FXML
    private TextField lUnit1;

    @FXML
    private TextField lUnit2;

    @FXML
    private Button loader;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextArea operationsArea;

    @FXML
    private TextField fileField;

    @FXML
    private Button writeButton;

    @FXML
    private Button save;

    @FXML
    private Label lines;

    @FXML
    private TextField unit1;

    @FXML
    private TextField unit2;

    @FXML
    private Label wordsLabel;

    @FXML
    private TextField wUnit1;

    @FXML
    private TextField wUnit2;

    @FXML
    private Button helpBt;

    @FXML
    private TextArea romContentsArea;

    /** Our list variable. All lines, that are to be modified,
     *  are stored in it.
     */
    private static List<String> list = new ArrayList<>();

    private static File file;

    /** Implicitly overridden initialize method()
     *  from the javaFX framework. It defines the
     *  settings of our variable at the start of the
     *  L-Switch app
     */
    @FXML
    public void initialize(){

        fileField.setEditable(false);
        operationsArea.setEditable(false);
        romContentsArea.setEditable(false);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String input = change.getText();
            if(input.matches("[0-9]*")){
                return change;
            }
            return null;
        };
        unit1.setTextFormatter(new TextFormatter<String>(filter));
        unit2.setTextFormatter(new TextFormatter<String>(filter));
        wUnit1.setTextFormatter(new TextFormatter<String>(filter));
        wUnit2.setTextFormatter(new TextFormatter<String>(filter));
        lUnit1.setTextFormatter(new TextFormatter<String>(filter));
        lUnit2.setTextFormatter(new TextFormatter<String>(filter));

    }

    /**  fileHandler() is used to load the contents of a text file
     *   from the ROM memory. Once collected, the contents of the file
     *   are written to the list variable.
     *   fileHandler() is explicitly mapped to the loader Button object
     *   in primary.fxml
     */
    @FXML
    public void fileHandler(){

        romContentsArea.clear();
        Stage st = new Stage();
        Stream<String> input = Stream.empty();
        FileChooser.ExtensionFilter filter =  new FileChooser.ExtensionFilter("Txt files",  "*.txt");
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Open txt file");
        file = fileChooser.showOpenDialog(st);
        if(file == null) return;

        fileField.setText(file.getAbsolutePath());

        try {
            if(file.exists()){
                input = Files.lines(Paths.get(file.getAbsolutePath()));
            }

        } catch (IOException e) {
            operationsArea.setText(e.getMessage());
        }finally {
            list = input.collect(Collectors.toList());
            list.forEach(a -> romContentsArea.appendText(a +"\n"));
        }

    }

    /**  helpHandler() is a method mapped to the help button in L-Switch.
     *   When pressed, it displays information about the functions of L-Switch.
     *   helpHandler() is explicitly mapped to the help button in primary.fxml
     */
    @FXML
    public void helpHandler(){

        operationsArea.clear();
        String helpMessage = "Load your file by pressing Load file.\n" +
                "Write the modified text to the file by pressing Save.\n" +
                "You may switch lines or words. Choose numbers in scope.\n" +
                "Indexing for words starts from one!!! ";
        operationsArea.setText(helpMessage);

    }

    /**  record() is mapped to the "Save" button in L-Switch,
     *   represented here as the object save of class Button.
     *   record() writes the modified contents to the file
     *   and calls the printContent() method to display them
     *   in the romContentsArea(labeled "Contents saved in ROM"),
     *   which shows the contents of the file,
     *   as they exist in the ROM memory.
     */
    @FXML
    public void record() {

        operationsArea.clear();
        romContentsArea.clear();

        try {
            Validator.validateList(list);

            writeToFile();
            list = printContent(romContentsArea, loadContent());

        } catch (EmptyListException e) {
            operationsArea.setText("NO RECORDS IN RANDOM ACCESS MEMORY");
        }
    }

    /** writeToFile() is an internal method,
     *  intended to be used by record() method
     *  to write the modified contents to the file
     */
    private void writeToFile(){

        BufferedWriter writer;
        FileWriter fileWriter;

        if(file != null){

            try {
                fileWriter = new FileWriter(file);
                writer = new BufferedWriter(fileWriter);
                for (String elem : list) {
                    writer.write(elem);
                    writer.newLine();
                }
                operationsArea.setText("FILE SUCCESSFULLY MODIFIED IN ROM");
                writer.close();
            } catch (IOException e) {
                operationsArea.setText(e.getMessage());
            }

        }

    }

    /**  switchLines() is the method used to switch the lines of a file, loaded
     *   in the L-Switch app. It is mapped to the "Compute switched lines button",
     *   represented here as the writeButton object. Explicit mapping is located
     *   in the primary.fxml file.
     *   switchLines() uses the static methods of the Validator class from the
     *   validator package to validate its contents.If it fails to validate them,
     *   an error message will be shown in the Operations text field.
     */
    @FXML
    public void switchLines(){

        operationsArea.clear();
        String val1,val2;
        int fLine,sLine;
        val1 = unit1.getText();
        val2 = unit2.getText();

        try {

            Validator.validateNumericInput(val1,val2);
            fLine = Integer.parseInt(unit1.getText());
            sLine = Integer.parseInt(unit2.getText());

            Validator.validateList(list);


            Validator.validateListIndexes(list,fLine,sLine);
            Collections.swap(list,fLine-1,sLine-1);
            list.forEach(a -> operationsArea.appendText(a + "\n"));

        } catch (NumericInputException e) {
            operationsArea.setText(e.getMessage());
        } catch (EmptyListException e) {
            operationsArea.setText(e.getMessage());
        } catch (InvalidIndexException e) {
            operationsArea.setText(e.getMessage() + list.size());
        }
    }

    /**  switchWords() is the method used to switch the lines of a file, loaded
     *   in the L-Switch app. It is mapped to the "Compute switched words button",
     *   represented here as the computeWords object. Explicit mapping is located
     *   in the primary.fxml file.
     *   switchWords() uses the static methods of the Validator class from the
     *   validator package to validate its contents. If it fails to validate them
     *   , an error message will be shown in the Operations text field.
     */
    @FXML
    public void switchWords(){

        operationsArea.clear();

        String val1,val2,val3,val4,placeholder;
        int fLine = 0,fWord,sLine = 0,sWord;

        ArrayList<List<String>> listOfLines = loadContent();

        val1 = wUnit1.getText();
        val2 = wUnit2.getText();
        val3 = lUnit1.getText();
        val4 = lUnit2.getText();

        try {
            Validator.validateNumericInput(val1,val2,val3,val4);
            fLine = Integer.parseInt(lUnit1.getText());
            sLine = Integer.parseInt(lUnit2.getText());
            fWord = Integer.parseInt(wUnit1.getText());
            sWord = Integer.parseInt(wUnit2.getText());
            Validator.validateListOfLists(listOfLines);

            Validator.validateWordsIndexes(listOfLines,fLine,sLine,fWord,sWord);

            placeholder = listOfLines.get(fLine-1).get(fWord-1);
            listOfLines.get(fLine-1).set(fWord-1,listOfLines.get(sLine-1).get(sWord-1));
            listOfLines.get(sLine-1).set(sWord-1,placeholder);

            list = printContent(operationsArea,listOfLines);

        } catch (NumericInputException e) {
            operationsArea.setText(e.getMessage());

        } catch (EmptyListException e) {
            operationsArea.setText(e.getMessage());

        } catch (InvalidIndexException e) {
            operationsArea.setText(e.getMessage() + listOfLines.size() + "\n");
            if((fLine-1) < listOfLines.size() && !((fLine-1) < 1)){
                operationsArea.appendText("AND AT LINE "+(fLine)+ " WORD INDEX BETWEEN 1 AND " + listOfLines.get(fLine-1).size() + "\n");
            }
            if((sLine-1) < listOfLines.size() && !((sLine-1) < 1)){
                operationsArea.appendText("AND AT LINE " +(sLine) + " WORD INDEX BETWEEN 1 AND " + listOfLines.get(sLine-1).size() + "\n");
            }
        }

    }

    /**       loadContent() is a method intended for internal use.
     *        It converts a list of lines to a list of lists of words,
     *        representing the words in file, loaded by L-Switch.
     * @return list of lists of words
     */
    private ArrayList<List<String>> loadContent(){

        ArrayList <List<String>> listOfSplitLines = new ArrayList<>();
        list.forEach(a -> listOfSplitLines.add(Arrays.asList(a.split("\\s"))));

        return listOfSplitLines;
    }

    /**            printContent() is used internally
     *             to show the modified lines in a TextArea
     *
     * @param area here used as a placeholder for one two TextArea objects,
     *             one which shows the file contents, as they existed written in
     *             memory, and one, which shows the file contents, as modified
     *             by the L-Switch user.
     *
     * @param list represents the list of lines, used in the L-Switch app
     *
     * @return     a modified list of lines
     */
    private List<String> printContent(TextArea area,List<List<String>> list){

        StringBuilder sb = new StringBuilder(" ");
        List <String> arrList = new ArrayList<>();

        for(List<String> l: list){
            for (String elem:l){
                sb.append(elem).append(" ");
            }
            arrList.add(sb.toString().trim());
            sb = new StringBuilder(" ");
        }

        area.clear();
        arrList.forEach(string -> area.appendText(string + "\n"));

        return arrList;
    }

    /**        checkReadContents checks the modified copy
     *         of the contents of the file, loaded in L-Switch,
     *
     * @return if a difference exists between the file contents in ROM memory,
     *          and the ones processed in the RAM memory, return true. Otherwise,
     *          a false value will be returned.
     */
    public boolean checkReadContents(){
        try {

            Stream<String> oldContents = Files.lines(Paths.get(file.getAbsolutePath()));
            List<String> oldContentsList = oldContents.collect(Collectors.toList());
            if(oldContentsList.size() == list.size()) {
                for (int i = 0; i < oldContentsList.size(); i++) {
                    if(!(oldContentsList.get(i).equals(list.get(i)))){
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            operationsArea.clear();
            operationsArea.setText(e.getMessage());
        }
        return false;
    }

    public File getFile(){
        return file;
    }

    public List<String> getList(){
        return list;
    }

}
