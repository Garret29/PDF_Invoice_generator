package pl.dtree.pdf_invoice_generator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import pl.dtree.pdf_invoice_generator.controller.GeneratorController;


public class GeneratorView extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("invoiceGeneratorView.fxml"));
        TabPane tabPane = loader.load();
        GeneratorController controller = loader.getController();

        //@SuppressWarnings("ConstantConditions") Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("invoiceGeneratorView.fxml"));
        //((GeneratorController)loader.getController()).setModel(model);

        /*
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return new GeneratorController(model);
            }
        });
        */


        Scene scene = new Scene(tabPane, 750, 500);
        stage.setTitle("Generator faktur Dtree");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) throws Exception {

        launch(args);
    }

}


