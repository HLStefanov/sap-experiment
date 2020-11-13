package termination;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TerminationStage extends Application {

    private  FXMLLoader fxmlLoader;
    private ExitController controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        fxmlLoader = new FXMLLoader(getClass().getResource("termstage.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        primaryStage.setTitle("Save file?");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 333, 101));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public ExitController getFxmlController() {

        return controller;
    }
}
