package pl.dtree.pdf_invoice_generator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import pl.dtree.pdf_invoice_generator.model.GeneratorModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class GeneratorController implements Initializable {


    @FXML
    public TextField recieverField;
    @FXML
    public TextField recieverField2;
    @FXML
    public TextField recieverBuildingField;
    @FXML
    public TextField recieverStreetField;
    @FXML
    public TextField recieverApartmentField;
    @FXML
    public TextField recieverPostalCodeField;
    @FXML
    public TextField recieverCityField;
    @FXML
    public TextField recieverNIPField;
    @FXML
    public TextField senderField;
    @FXML
    public TextField senderField2;
    @FXML
    public TextField senderStreetField;
    @FXML
    public TextField senderBuildingField;
    @FXML
    public TextField senderApartmentField;
    @FXML
    public TextField senderPostalCodeField;
    @FXML
    public TextField senderCityField;
    @FXML
    public TextField senderNIPField;
    @FXML
    public TextField senderAccountField;
    @FXML
    public TextField invoiceIDField;
    @FXML
    public TextField invoiceServiceField;
    @FXML
    public TextField invoiceValueField;
    @FXML
    public TextField invoiceInWordsValueField;
    @FXML
    public TextField invoicePaymentDateField;
    @FXML
    public TextField invoiceDateField;
    @FXML
    public TextField invoiceCityField;
    public ScrollPane scrollPane;

    @FXML
    ImageView logoImage;

    private Image image;
    private GeneratorModel model;
    private Hashtable<String, String> invoiceData;

    public void updateData() {

    }

    @FXML
    public void generateFileAction(ActionEvent actionEvent) {
        FileChooser saveChooser = new FileChooser();
        Window stage = ((Node) actionEvent.getSource()).getScene().getWindow();
        saveChooser.setTitle("Zapisz plik");
        saveChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        saveChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("PDF file", "*.pdf"));
        saveChooser.setInitialFileName("faktura.pdf");
        File file = saveChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                model.setInvoiceData(invoiceData);
                model.generatePDF(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void changeLogoAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        Window stage = ((Node) actionEvent.getSource()).getScene().getWindow();
        chooser.setTitle("Wybierz logo");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image files(jpeg, jpg, png)", "*.jpeg", "*.jpg", "*.png"));
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            image = new Image(file.toURI().toString());
            logoImage.setImage(image);
            model.setLogoImage(file);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (image != null) {
            logoImage.setImage(image);
        }

        scrollPane.setFitToWidth(true);

        /*
        postalCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("\\d\\d-\\d\\d\\d", postalCodeField.getCharacters())) {
                    wrongCodeLabel.setVisible(false);
                } else wrongCodeLabel.setVisible(true);
            }
        });

        valueField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\d+\\s{1})*)\\d+$)?", valueField.getCharacters())) {
                    wrongValueLabel.setVisible(false);
                } else wrongValueLabel.setVisible(true);
            }
        });

        inWordsValueField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\p{javaAlphabetic}+\\s{1})*)\\p{javaAlphabetic}+$)?", inWordsValueField.getCharacters())) {
                    wrongValueLabel2.setVisible(false);
                } else wrongValueLabel2.setVisible(true);
            }
        });
        */

        if (model == null) {
            prepareController(new GeneratorModel());
        }
        updateData();
    }

    public void prepareController(GeneratorModel model) {
        setModel(model);
        setInvoiceData(new Hashtable<>());
    }

    public void setModel(GeneratorModel model) {
        this.model = model;
    }

    public Hashtable<String, String> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(Hashtable<String, String> invoiceData) {
        this.invoiceData = invoiceData;
    }

}
