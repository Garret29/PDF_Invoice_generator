package pl.dtree.pdf_invoice_generator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.dtree.pdf_invoice_generator.controller.GeneratorController;

import java.io.IOException;


public class GeneratorView extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));

        GridPane gridPane = null;
        try {
            gridPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GeneratorController controller = loader.getController();

        Scene scene = new Scene(gridPane, 900, 450);
        stage.setTitle("Generator faktur Dtree");
        stage.setScene(scene);
        stage.setMaxHeight(842.0);
        stage.setMaxWidth(1366.0);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}


