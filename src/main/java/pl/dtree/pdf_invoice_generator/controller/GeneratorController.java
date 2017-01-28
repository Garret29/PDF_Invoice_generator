package pl.dtree.pdf_invoice_generator.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.json.JSONObject;
import pl.dtree.pdf_invoice_generator.model.GeneratorModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GeneratorController implements Initializable {


    @FXML
    public TextField receiverField;
    @FXML
    public TextField receiverField2;
    @FXML
    public TextField receiverBuildingField;
    @FXML
    public TextField receiverStreetField;
    @FXML
    public TextField receiverApartmentField;
    @FXML
    public TextField receiverPostalCodeField;
    @FXML
    public TextField receiverCityField;
    @FXML
    public TextField receiverNIPField;

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

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Label wrongValueLabel2;
    @FXML
    public Label wrongValueLabel;
    @FXML
    public Label wrongCodeLabel2;
    @FXML
    public Label wrongNIPLabel2;
    @FXML
    public Label wrongCodeLabel;
    @FXML
    public Label wrongNIPLabel;
    @FXML
    ImageView logoImage;

    private Image image;
    private GeneratorModel model;
    private JSONObject invoiceData;

    public void updateData() {
        invoiceData.put("receiver", receiverField.getText());
        invoiceData.put("receiverMore", receiverField2.getText());
        invoiceData.put("receiverBuilding", receiverBuildingField.getText());
        invoiceData.put("receiverApartment", receiverApartmentField.getText());
        invoiceData.put("receiverPostalCode", receiverPostalCodeField.getText());
        invoiceData.put("receiverStreet", receiverStreetField.getText());
        invoiceData.put("receiverCity", receiverCityField.getText());
        invoiceData.put("receiverNIP", receiverNIPField.getText());
        invoiceData.put("sender", senderField.getText());
        invoiceData.put("senderMore", senderField2.getText());
        invoiceData.put("senderBuilding", senderBuildingField.getText());
        invoiceData.put("senderApartment", senderApartmentField.getText());
        invoiceData.put("senderPostalCode", senderPostalCodeField.getText());
        invoiceData.put("senderStreet", senderStreetField.getText());
        invoiceData.put("senderCity", senderCityField.getText());
        invoiceData.put("senderNIP", senderNIPField.getText());
        invoiceData.put("senderAccountNumber", senderAccountField.getText());
        invoiceData.put("invoiceID", invoiceIDField.getText());
        invoiceData.put("invoiceService", invoiceServiceField.getText());
        invoiceData.put("invoiceValue", invoiceValueField.getText());
        invoiceData.put("invoiceInWordsValue", invoiceInWordsValueField.getText());
        invoiceData.put("invoicePaymentDate", invoicePaymentDateField.getText());
        invoiceData.put("invoiceDate", invoiceDateField.getText());
        invoiceData.put("invoiceCity", invoiceCityField.getText());
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


        receiverPostalCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(\\d\\d-\\d\\d\\d)?", receiverPostalCodeField.getCharacters())) {
                    wrongCodeLabel2.setVisible(false);
                } else wrongCodeLabel2.setVisible(true);
            }
        });

        senderPostalCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(\\d\\d-\\d\\d\\d)?", senderPostalCodeField.getCharacters())) {
                    wrongCodeLabel.setVisible(false);
                } else wrongCodeLabel.setVisible(true);
            }
        });

        invoiceValueField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\d+\\s{1})*)\\d+$)?", invoiceValueField.getCharacters())) {
                    wrongValueLabel2.setVisible(false);
                } else wrongValueLabel2.setVisible(true);
            }
        });

        invoiceInWordsValueField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\p{javaAlphabetic}+\\s{1})*)\\p{javaAlphabetic}+$)?", invoiceInWordsValueField.getCharacters())) {
                    wrongValueLabel.setVisible(false);
                } else wrongValueLabel.setVisible(true);
            }
        });


        if (model == null) {
            setModel(new GeneratorModel());
            setInvoiceData(new JSONObject());
        }
        updateData();
    }

    public void setModel(GeneratorModel model) {
        this.model = model;
    }

    public JSONObject getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(JSONObject invoiceData) {
        this.invoiceData = invoiceData;
    }

}
