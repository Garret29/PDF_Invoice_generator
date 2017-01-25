package pl.dtree.pdf_invoice_generator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private Image image;

    private GeneratorModel model;

    private Hashtable<String, String > invoiceData;

    public void updateData() {
        invoiceData.put("paymentDate", paymentDateField.getText());
        invoiceData.put("invoiceValue", valueField.getText());
        invoiceData.put("invoiceService", serviceField.getText());
        invoiceData.put("recieverCity", cityField.getText());
        invoiceData.put("recieverPostalCode", postalCodeField.getText());
        invoiceData.put("InvoicePlaceAndDate", placeAndDateField.getText());
        invoiceData.put("reciever", recieverField.getText());
        invoiceData.put("recieverStreet", streetField.getText());
        invoiceData.put("invoiceID", invoiceIdField.getText());
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
        if(file!=null) {
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
