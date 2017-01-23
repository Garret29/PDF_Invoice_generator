package pl.dtree.pdf_invoice_generator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;


public class GeneratorView extends Application implements Observer{

    @Override
    public void start(Stage stage) throws Exception {

        @SuppressWarnings("ConstantConditions") Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("invoiceGeneratorView.fxml"));

        Scene scene = new Scene(root, 750, 500);

        stage.setTitle("Generator faktur Dtree");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}


