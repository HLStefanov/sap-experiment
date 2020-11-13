package mainstage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import termination.ExitController;
import termination.TerminationStage;

import java.io.File;
import java.util.List;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        primaryStage.setTitle("L-Switch");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        setActionOnXButton(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**           This method adds an EventHandler to the X button
     *            of the L-Switch main window, which contains a call to
     *            the runLater() method of the java platform, which in
     *            our case is used to display the closing window of our
     *            application. The aforementioned window, implemented
     *            in the termination package will only appear if a file's
     *            contents are copied in memory,have been modified,
     *            and are thus different to the contents of the
     *            file located in ROM.
     * @param primaryStage is the primary stage of the L-Switch app
     */
    public void setActionOnXButton(Stage primaryStage){
        primaryStage.setOnCloseRequest( event -> {

            Controller localController = new Controller();

            List<String> list = localController.getList();
            File file = localController.getFile();
            if (file != null && localController.checkReadContents()) {

                Platform.runLater(() -> {

                    try {

                        TerminationStage tm = new TerminationStage();
                        tm.start(new Stage());
                        ExitController controller1 = tm.getFxmlController();
                        controller1.setFile(file);
                        controller1.setList(list);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.exit();
                    }
                });
            }else Platform.exit();
        });
    }
}