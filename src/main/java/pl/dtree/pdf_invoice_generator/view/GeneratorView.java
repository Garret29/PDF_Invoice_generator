package pl.dtree.pdf_invoice_generator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GeneratorView extends Application {




    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("invoiceGeneratorView.fxml"));

        Scene scene = new Scene(root, 750, 500);

        stage.setTitle("Generator faktur Dtree");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    public static void main(String[] args) throws Exception {
        launch(args);
    }
}


