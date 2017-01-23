package pl.dtree.pdf_invoice_generator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import pl.dtree.pdf_invoice_generator.controller.GeneratorController;
import pl.dtree.pdf_invoice_generator.model.GeneratorModel;

import java.util.Observable;
import java.util.Observer;


public class GeneratorView extends Application implements Observer{

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        GeneratorModel model = new GeneratorModel();

        //FXMLLoader loader = new FXMLLoader();

        @SuppressWarnings("ConstantConditions") Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("invoiceGeneratorView.fxml"));
        /*
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return new GeneratorController(model);
            }
        });
        */

        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Generator faktur Dtree");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) throws Exception {
        GeneratorView view = new GeneratorView();

        launch(args);
    }

}


