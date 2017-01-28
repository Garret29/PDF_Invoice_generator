package pl.dtree.pdf_invoice_generator.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.json.JSONException;
import org.json.JSONObject;
import pl.dtree.pdf_invoice_generator.model.GeneratorModel;

import java.io.*;
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
    MenuButton receiverMenuButton;
    @FXML
    MenuButton senderMenuButton;
    @FXML
    GridPane invoiceFormPane;
    @FXML
    ImageView logoImage;

    private Image image;
    private GeneratorModel model;
    private JSONObject invoiceData;
    private JSONObject senderData;
    private JSONObject receiverData;


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

        senderNIPField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\d+\\s{1})*)\\d+$)?", senderNIPField.getCharacters())) {
                    wrongNIPLabel.setVisible(false);
                } else wrongNIPLabel.setVisible(true);
            }
        });

        receiverNIPField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("(((\\d+\\s{1})*)\\d+$)?", receiverNIPField.getCharacters())) {
                    wrongNIPLabel2.setVisible(false);
                } else wrongNIPLabel2.setVisible(true);
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

        for (Node node : invoiceFormPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (((TextField) node).getText().length() > 40) {
                            String s = ((TextField) node).getText().substring(0, 40);
                            ((TextField) node).setText(s);
                        }
                    }
                });
            }
        }

        if (model == null) {
            setModel(new GeneratorModel());
            setInvoiceData(new JSONObject());
            setSenderData(new JSONObject());
            setReceiverData(new JSONObject());

        }
        updateData();
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
                model.setReceiverData(receiverData);
                model.setSenderData(senderData);
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

    public void loadReceiverData(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        Window stage = receiverMenuButton.getScene().getWindow();
        chooser.setTitle("Wybierz plik z danymi odbiorcy");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            String jsonData = null;
            try {
                InputStream inputStream = new FileInputStream(file);
                jsonData = org.apache.commons.io.IOUtils.toString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonData != null) {
                fillReceiverData(new JSONObject(jsonData));
            }
        }

    }

    public void loadSenderData(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        Window stage = senderMenuButton.getScene().getWindow();
        chooser.setTitle("Wybierz plik z danymi wystawcy");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            String jsonData = null;
            try {
                InputStream inputStream = new FileInputStream(file);
                jsonData = org.apache.commons.io.IOUtils.toString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonData != null) {
                fillSenderData(new JSONObject(jsonData));
            }
        }
    }

    public void saveSenderData(ActionEvent actionEvent) {
        FileChooser saveChooser = new FileChooser();
        Window stage = senderMenuButton.getScene().getWindow();
        saveChooser.setTitle("Zapisz dane wystawcy");
        saveChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        saveChooser.setInitialFileName("File.txt");
        File file = saveChooser.showSaveDialog(stage);

        if (file != null) {
            updateData();
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(senderData.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveReceiverData(ActionEvent actionEvent) {
        FileChooser saveChooser = new FileChooser();
        Window stage = receiverMenuButton.getScene().getWindow();
        saveChooser.setTitle("Zapisz dane odbiorcy");
        saveChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        saveChooser.setInitialFileName("file.txt");
        File file = saveChooser.showSaveDialog(stage);

        if (file != null) {
            updateData();
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(receiverData.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public void updateData() {
        receiverData.put("name", receiverField.getText());
        receiverData.put("nameMore", receiverField2.getText());
        receiverData.put("building", receiverBuildingField.getText());
        receiverData.put("apartment", receiverApartmentField.getText());
        receiverData.put("postalCode", receiverPostalCodeField.getText());
        receiverData.put("street", receiverStreetField.getText());
        receiverData.put("city", receiverCityField.getText());
        receiverData.put("NIP", receiverNIPField.getText());
        senderData.put("sender", senderField.getText());
        senderData.put("senderMore", senderField2.getText());
        senderData.put("building", senderBuildingField.getText());
        senderData.put("apartment", senderApartmentField.getText());
        senderData.put("postalCode", senderPostalCodeField.getText());
        senderData.put("street", senderStreetField.getText());
        senderData.put("city", senderCityField.getText());
        senderData.put("NIP", senderNIPField.getText());
        senderData.put("accountNumber", senderAccountField.getText());
        invoiceData.put("ID", invoiceIDField.getText());
        invoiceData.put("service", invoiceServiceField.getText());
        invoiceData.put("value", invoiceValueField.getText());
        invoiceData.put("inWordsValue", invoiceInWordsValueField.getText());
        invoiceData.put("paymentDate", invoicePaymentDateField.getText());
        invoiceData.put("date", invoiceDateField.getText());
        invoiceData.put("city", invoiceCityField.getText());
    }

    private void fillReceiverData(JSONObject receiverDataFile) {
        receiverField.setText(receiverDataFile.getString("name"));
        receiverField2.setText(receiverDataFile.getString("nameMore"));
        receiverBuildingField.setText(receiverDataFile.getString("building"));
        receiverApartmentField.setText(receiverDataFile.getString("apartment"));
        receiverPostalCodeField.setText(receiverDataFile.getString("postalCode"));
        receiverCityField.setText(receiverDataFile.getString("city"));
        receiverStreetField.setText(receiverDataFile.getString("street"));
        receiverNIPField.setText(receiverDataFile.getString("NIP"));
    }

    private void fillSenderData(JSONObject senderDataFile) {
        senderField.setText(senderDataFile.getString("name"));
        senderField2.setText(senderDataFile.getString("nameMore"));
        senderBuildingField.setText(senderDataFile.getString("building"));
        senderApartmentField.setText(senderDataFile.getString("apartment"));
        senderPostalCodeField.setText(senderDataFile.getString("postalCode"));
        senderCityField.setText(senderDataFile.getString("city"));
        senderStreetField.setText(senderDataFile.getString("street"));
        senderNIPField.setText(senderDataFile.getString("NIP"));

        try {
            senderAccountField.setText(senderDataFile.getString("accountNumber"));
        } catch (JSONException e) {
            senderAccountField.setText("");
        }

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

    public void setSenderData(JSONObject senderData) {
        this.senderData = senderData;
    }

    public void setReceiverData(JSONObject receiverData) {
        this.receiverData = receiverData;
    }
}
