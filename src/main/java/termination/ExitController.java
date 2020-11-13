package termination;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExitController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button delete;

    @FXML
    private Button saveInMemory;

    @FXML
    private Label message;

    private File file;

    private List<String> list;

    /**  An implicitly overridden version of the initialize() method,
     *   distributed with JavaFX. Here it is used to set the contents
     *   of the main label of L-Switch's exit stage.
     */
    @FXML
    public void initialize(){
        message.setWrapText(true);
        message.setText("Save your file in memory or delete the modified content.");
    }

    /**                    saveInFile() is called if the user presses
     *                     the save button, implemented here as the saveInMemory
     *                     variable. The mapping is explicitly defined in termstage.fxml.
     *                     On keypress, all contents modified in RAM are saved in ROM.
     * @throws IOException if the file is not found
     */
    @FXML
    public void saveInFile() throws IOException {
        BufferedWriter writer;
        FileWriter fileWriter;

        if(file != null){

            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
            for (String elem : list) {
                writer.write(elem);
                writer.newLine();

            }
            writer.close();
        }
        Platform.exit();
    }

    /**  destroyLoadedFile() exits the java Platform.
     *   It is called if the user presses the "Don't save"
     *   button in L-Switch. The mapping of this method
     *   to the delete Button variable is explicitly stated
     *   in termstage.fxml.
     */
    @FXML
    public void destroyLoadedFile() {
        Platform.exit();
    }

    public void setFile(File file){
        this.file = file;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
