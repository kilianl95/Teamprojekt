package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View.fxml"));

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initialize(primaryStage);

        SatSolverRekursiv solver = new SatSolverRekursiv();

        Model model = new Model(solver);
        controller.setModel(model);


        Scene scene = new Scene(root, 1280, 844);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SatSolver");
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
