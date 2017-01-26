package pl.dtree.pdf_invoice_generator.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
import java.util.regex.Pattern;

public class GeneratorController implements Initializable {

    @FXML
    public Button defaultSetButton;
    @FXML
    TextField currencyField;
    @FXML
    ToggleButton toggleDefaultDataButton;
    @FXML
    Label wrongValueLabel2;
    @FXML
    Label wrongValueLabel;
    @FXML
    Label wrongCodeLabel;
    @FXML
    ImageView logoImage;
    @FXML
    TextField paymentDateField;
    @FXML
    TextField valueField;
    @FXML
    TextField serviceField;
    @FXML
    TextField cityField;
    @FXML
    TextField postalCodeField;
    @FXML
    TextField placeAndDateField;
    @FXML
    TextField recieverField;
    @FXML
    TextField streetField;
    @FXML
    TextField invoiceIdField;
    @FXML
    TextField inWordsValueField;
    @FXML
    TextField senderCityField;
    @FXML
    TextField senderPostalCodeField;
    @FXML
    TextField senderStreetField;
    @FXML
    TextField senderNameDataField;
    @FXML
    TextField senderCompanyField;
    @FXML
    TextField senderNIPField;
    private Image image;
    private GeneratorModel model;
    private Hashtable<String, String> invoiceData;

    public void updateData() {
        invoiceData.put("paymentDate", paymentDateField.getText());
        invoiceData.put("invoiceValue", valueField.getText());
        invoiceData.put("invoiceService", serviceField.getText());
        invoiceData.put("recieverCity", cityField.getText());
        invoiceData.put("recieverPostalCode", postalCodeField.getText());
        invoiceData.put("invoicePlaceAndDate", placeAndDateField.getText());
        invoiceData.put("reciever", recieverField.getText());
        invoiceData.put("recieverStreet", streetField.getText());
        invoiceData.put("invoiceID", invoiceIdField.getText());
        invoiceData.put("senderCity", senderCityField.getText());
        invoiceData.put("senderPostalCode", senderPostalCodeField.getText());
        invoiceData.put("senderStreet", senderStreetField.getText());
        invoiceData.put("sender", senderNameDataField.getText());
        invoiceData.put("senderCompany", senderCompanyField.getText());
        invoiceData.put("currency", currencyField.getText());
        invoiceData.put("inWordsValue", inWordsValueField.getText());
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

    @FXML
    public void senderChangedAction(ActionEvent actionEvent) {
        if (toggleDefaultDataButton.isSelected()) {
            toggleDefaultDataButton.setText("Niedomyślne");

            senderStreetField.setDisable(true);
            senderPostalCodeField.setDisable(true);
            senderCityField.setDisable(true);
            senderCompanyField.setDisable(true);
            senderNameDataField.setDisable(true);
            senderNIPField.setDisable(true);
            defaultSetButton.setDisable(true);


        } else {

            toggleDefaultDataButton.setText("Domyślne");

            senderStreetField.setDisable(false);
            senderPostalCodeField.setDisable(false);
            senderCityField.setDisable(false);
            senderCompanyField.setDisable(false);
            senderNameDataField.setDisable(false);
            senderNIPField.setDisable(false);
            defaultSetButton.setDisable(false);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (image != null) {
            logoImage.setImage(image);
        }

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
